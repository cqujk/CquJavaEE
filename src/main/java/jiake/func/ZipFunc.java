package jiake.func;

import jiake.global.DisplayManager;
import jiake.global.ComType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import static jiake.global.DisplayManager.printProgressBar;
import static jiake.global.DisplayManager.showCompletionDialog;
import static jiake.global.ThreadPoolManager.getExecutor;
import static jiake.tool.AnsiColor.*;
import static jiake.tool.PathTool.constructOutputPath;

public class ZipFunc extends AbstractFunc {
    private final DisplayManager displayManager = DisplayManager.getInstance();
    private static final Logger logger = LogManager.getLogger(ZipFunc.class);
    int flag = 0;
    int progress = 0;

    long startTime = 0;
    long bytesSinceLastUpdate = 0;
    //    Timer timer = new Timer();
    volatile boolean done = false;
    AtomicLong currentSize = new AtomicLong(0);
    private static final ExecutorService executor = getExecutor();

    @SafeVarargs
    @Override
    public final <T, R> R func(T... args) {
        logger.info("zip中收到的指令是" + Arrays.toString(args));
        assert (args.length == 3) : "zip指令参数个数错误";
        String command = (String) args[0];
        String source = (String) args[1];
        String target = (String) args[2];
        System.out.println(BLUE + "0." + YELLOW + "后台" + RESET);
        System.out.println(BLUE + "1." + YELLOW + "前台" + RESET);
        System.out.println(PURPLE + "选择前台还是后台执行" + RESET);
        Scanner scanner = new Scanner(System.in);
        flag = Integer.parseInt(scanner.nextLine());
        if (flag == 0) {//开启新线程，和原主线程不一致，独立执行
            executor.submit(() -> {
                startTime = System.nanoTime();
                try {
                    switch (command) {
                        case "zip":
                            String outputZipPath = constructOutputPath(source, target, ComType.ZIP);
                            zip(source, outputZipPath);
                            break;
                        case "unzip":
                            String outputUnzipPath = constructOutputPath(source, target, ComType.UNZIP);
                            unzip(source, outputUnzipPath);
                            break;
                    }
                } catch (IOException e) {
                    logger.error("处理zip/unzip命令时发生错误: " + e.getMessage(), e);
                }
                long endTime = System.nanoTime();
                logger.info("zip/unzip指令执行完毕,应该构筑窗口了");
                showCompletionDialog(command + "指令执行成功", "耗时" + (double) (endTime - startTime) / 1_000_000_000 + "秒");
                logger.info("窗口应该构造出来了");
            });
        } else {
            try {
                switch (command) {
                    case "zip":
                        String outputZipPath = constructOutputPath(source, target, ComType.ZIP);
                        zip(source, outputZipPath);
                        break;
                    case "unzip":
                        String outputUnzipPath = constructOutputPath(source, target, ComType.UNZIP);
                        unzip(source, outputUnzipPath);
                        break;
                }
            } catch (IOException e) {
                logger.error("处理zip/unzip命令时发生错误: " + e.getMessage(), e);
            }
        }
        return (R) "已执行";
    }

    private void zip(String source, String target) throws IOException {
        File targetFile = new File(target);
        // 确保目标路径是一个文件路径，是以.zip为结尾的文件名
        if (targetFile.isDirectory()) {
            throw new IllegalArgumentException("目标路径必须是一个文件路径，而不是目录路径");
        }
        FileOutputStream fos = new FileOutputStream(targetFile);
        ZipOutputStream zos = new ZipOutputStream(fos);
        logger.info("已为文件" + target + "创建输出流");
        File dir = new File(source);
        long totalSize = getTotalSize(dir);
        startTime = System.nanoTime();
        if (flag == 1) {
            System.out.println(CYAN + "正在压缩文件  " + ITALIC + UNDERLINE + YELLOW + source + RESET + CYAN + " 到 " + ITALIC + UNDERLINE + YELLOW + target + RESET);
            DisplayManager.getInstance().MainShow(() -> {
                // 启动定时器任务
                ScheduledExecutorService task = Executors.newSingleThreadScheduledExecutor();
                task.scheduleAtFixedRate(() -> {
                    //System.out.println("这一步执行了，要打印了");
                    if (!done) {
                        printProgressBar(progress);
                        if (progress == 100) {
                            task.shutdown();
                            //System.out.println("\n已经结束了");
                            done = true;
                            System.out.println();
                        }
                    }
                }, 0, 5, TimeUnit.MILLISECONDS);
                try {
                    if (dir.isDirectory()) {
                        zipDirectory(dir, "", zos, totalSize);
                    } else {
                        zipFile(dir, dir.getName(), zos, totalSize);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (!task.isTerminated()) {
                    try {
                        //System.out.println("这条执行了，不然又会乱了");
                        task.awaitTermination(5, TimeUnit.MINUTES); // 等待定时器任务完成
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                long endTime = System.nanoTime();
                System.out.println(GREEN + "压缩完成，耗时" + (double) (endTime - startTime) / 1_000_000_000 + "秒" + RESET);
            });
        } else {
            try {//依据源文件到底是文件夹还是文件
                if (dir.isDirectory()) {
                    zipDirectory(dir, "", zos, totalSize);
                } else {
                    zipFile(dir, dir.getName(), zos, totalSize);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        zos.close();
        fos.close();
        logger.info("压缩完成: " + target);
    }

    private long getTotalSize(File file) {
        if (file.isFile()) {
            return file.length();
        }
        long totalSize = 0;
        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                totalSize += getTotalSize(f);
            }
        }
        return totalSize;
    }

    private void zipDirectory(File curDir, String baseDir, ZipOutputStream zos, long totalSize) throws IOException {
        String name;
        if (baseDir == null || baseDir.isEmpty()) {
            name = curDir.getName();
        } else {
            name = baseDir + "/" + curDir.getName();
        }
        logger.info("此时处理的文件为" + curDir.getName() + "，其父目录为" + curDir.getParentFile().getName() + ",baseDir为" + baseDir + "生成的名字name为" + name);
        if (curDir.isDirectory()) {
            File[] files = curDir.listFiles();
            if (files != null) {
                for (File child : files) {
                    zipDirectory(child, name, zos, totalSize);
                }
            }
        } else {
            zipFile(curDir, name, zos, totalSize);
        }
    }

    private void zipFile(File sourceFile, String entryName, ZipOutputStream zos, long totalSize) throws IOException {
        logger.debug("识别到源地址" + sourceFile.getAbsolutePath() + "为文件类型");
        FileInputStream fis = new FileInputStream(sourceFile);
        ZipEntry zipEntry = new ZipEntry(entryName);
        zos.putNextEntry(zipEntry);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = fis.read(buffer)) > 0) {
            zos.write(buffer, 0, length);
            bytesSinceLastUpdate += length;
            if (bytesSinceLastUpdate >= 1024) { // 每1024字节更新一次
                currentSize.addAndGet(bytesSinceLastUpdate);
                bytesSinceLastUpdate = 0;
                //向上取整，防止卡在99%
                progress = (int) Math.ceil((double) (currentSize.get() * 100) / totalSize);
            }
        }
        if (bytesSinceLastUpdate > 0) {
            currentSize.addAndGet(bytesSinceLastUpdate);
        }
        zos.closeEntry();
        fis.close();
    }


    private void unzip(String sourceZip, String targetDir) throws IOException {
        File dir = new File(targetDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        FileInputStream fis = new FileInputStream(sourceZip);
        ZipInputStream zis = new ZipInputStream(fis);
        //displayManager.MainShow(() -> {
        //System.out.println("正在解压文件" + sourceZip + "到" + targetDir);
        ZipEntry zipEntry;
        while (true) {
            try {
                if ((zipEntry = zis.getNextEntry()) == null) {
                    break;
                }
                File file = new File(dir, zipEntry.getName());
                if (zipEntry.isDirectory()) {
                    file.mkdirs();
                } else {
                    File parent = file.getParentFile();
                    if (parent != null && !parent.exists()) {
                        parent.mkdirs();
                    }
                    FileOutputStream fos = new FileOutputStream(file);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, length);
                    }
                    fos.close();
                }
                zis.closeEntry();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        //});
        zis.close();
        fis.close();
        logger.info("解压完成: " + targetDir);
    }
}
