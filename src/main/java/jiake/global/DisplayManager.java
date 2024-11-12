package jiake.global;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static jiake.tool.AnsiColor.*;

public class DisplayManager {
    private static final Logger logger = LogManager.getLogger(DisplayManager.class.getName());
    private static DisplayManager instance;
    public static synchronized DisplayManager getInstance() {
        if (instance == null) {
            instance = new DisplayManager();
        }
        return instance;
    }
    private DisplayManager() {}
    // 使用 synchronized 关键字确保同一时刻只有一个线程可以执行 MainShow 方法
    public synchronized void MainShow(Runnable displayAction) {
        displayAction.run();
    }

    // 重载 MainShow 方法，接受一个 Consumer<String> 参数
    public synchronized void MainShow(String message, Consumer<String> displayAction) {
        displayAction.accept(message);
    }

    public synchronized void TopShow(Runnable displayAction) {
        displayAction.run();
    }
    public void showAllFiles(String path) {
        Path showPath = Paths.get(path);
        try (Stream<Path> stream = Files.list(showPath)) {
            // 将Path对象流转换为文件名的字符串流
            Stream<String> fileNames = stream.map(p -> p.getFileName().toString());
            // 将字符串流收集到一个列表中
            List<String> filesList = fileNames.collect(Collectors.toList());
            // 打印所有文件名称
            filesList.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void showFiles(String path) {
        File currentDir = new File(path);
        File[] files = currentDir.listFiles();
        // 显示文件列表
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.printf("| %-20s%-15s%-18s %-17s  |%n", CYAN + "类型", "最近修改日期", "文件大小（B）", "文件名称" + RESET);
        for (File file : files) {
            BasicFileAttributes attrs = null;
            try {
                attrs = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String type = file.isDirectory() ? "DIR" : "FILE";
            long size = file.length();
            String lastModified = dateFormat.format(file.lastModified());
            System.out.printf("| %-20s%-32s%-18d%-23s |%n", GREEN + type + RESET, lastModified + YELLOW, size, RESET + file.getName());
        }
    }
    public static void waitForUserInput() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(YELLOW+"按回车键继续..."+RESET);
        try {
            reader.readLine();
        } catch (IOException e) {
            System.out.println("读取用户输入时出错: " + e.getMessage());
        }
    }
    /**
     * 显示一个完成对话框
     * 该方法用于在图形界面上向用户显示一个对话框，通知某项操作或任务已经完成
     * 它使用SwingUtilities.invokeLater来确保对话框的显示是在事件调度线程(EDT)中进行的，
     * 这是Swing组件安全访问的要求
     *
     * @param title  对话框的标题，例如"操作完成"
     * @param message 对话框中显示的消息内容，例如"任务已经成功完成"
     */
    public static void showCompletionDialog(String title, String message) {
        logger.info("显示完成对话框: " + title + ", " + message);
        // 使用SwingUtilities.invokeLater确保图形界面的操作在EDT线程中执行
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("20220669JiaKe");
            //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 200);
            // 设置JFrame居中显示
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            // 确保 Swing 环境已经初始化
            if (!SwingUtilities.isEventDispatchThread()) {
                logger.warn("当前线程不是 EDT，需初始化 Swing 环境");
            }
            // 显示信息对话框，参数分别为父组件（此处为null，表示无父组件）、消息内容、对话框标题和消息类型
            JOptionPane.showMessageDialog(frame, message, title, JOptionPane.INFORMATION_MESSAGE);
            // 关闭JFrame
            frame.dispose();
        });
        logger.info("完成对话框显示完成");
    }
    public static void printProgressBar(int progress) {
        StringBuilder bar = new StringBuilder("[");
        int barLength = 50;
        int filledLength = (int) (barLength * progress / 100.0);
        for (int i = 0; i < barLength; i++) {
            if (i < filledLength) {
                bar.append(GREEN).append("=").append(RESET);
            } else {
                bar.append(" ");
            }
        }
        bar.append("] ").append(progress).append("%");
        System.out.print("\r" + bar.toString());
        System.out.flush(); // 强制刷新输出流
    }
//    public void clearTopShow() {
//        System.out.println("\033[H\033[2J"); // 清屏
//        System.out.println("------------------------------");
//    }
//    public void clearMainShow() {
//        System.out.println("\033[H\033[2J"); // 清屏
//    }
}
