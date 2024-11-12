package jiake.contro;

import jiake.func.*;
import jiake.global.ComType;
import jiake.global.DisplayManager;
import jiake.tool.AnsiColor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import static jiake.tool.PathTool.constructOutputPath;
import static jiake.tool.PathTool.toAbsolutePath;

public class CommandResolve {
    private static final Logger logger = LogManager.getLogger(CommandController.class);
    private static final DisplayManager displayManager= DisplayManager.getInstance();
    public static void handleEncry(String[] parts){
        if(parts.length < 2){
            System.out.println(AnsiColor.RED+"需指定加密文件路径"+ AnsiColor.RESET);
            return;
        }
        if(parts.length>3){
            System.out.println(AnsiColor.RED+"参数过多"+ AnsiColor.RESET);
        }
        String oriPath= toAbsolutePath(CommandController.getCurrentPath(),parts[1]);
        if(!new File(oriPath).exists()){
            System.out.println(AnsiColor.RED+"源文件不存在"+ AnsiColor.RESET);
        }
        if(!new File(oriPath).isFile()){
            System.out.println(AnsiColor.RED+"源文件必须为文件"+ AnsiColor.RESET);
        }
        String tarPath=(parts.length==3)? toAbsolutePath(CommandController.getCurrentPath(),parts[2]):CommandController.getCurrentPath();
        if(!new File(tarPath).isDirectory()){
            System.out.println(AnsiColor.RED+"目标路径必须为目录"+ AnsiColor.RESET);
            return;
        }
        if(!new File(tarPath).exists()){
            System.out.println(AnsiColor.RED+"目标路径不存在，将自动创建"+ AnsiColor.RESET);
            return;
        }
        logger.info("依据目的路径，构造输出路径前的tarPath是"+tarPath);
        String result=new FileEncryptorFunc().func(parts[0],oriPath,tarPath);
        System.out.println(AnsiColor.BLUE+"指令执行情况："+ AnsiColor.GREEN+result+ AnsiColor.RESET);
    }
    public static void handleCp(String[] parts) {
        if(parts.length < 2){
            System.out.println(AnsiColor.RED+"需指定拷贝文件路径"+ AnsiColor.RESET);
            return;
        }
        if(parts.length>3){
            System.out.println(AnsiColor.RED+"参数过多"+ AnsiColor.RESET);
            return;
        }
        String oriPath= toAbsolutePath(CommandController.getCurrentPath(),parts[1]);
        if(!new File(oriPath).exists()){
            System.out.println(AnsiColor.RED+"源文件或目录不存在"+ AnsiColor.RESET);
            return;
        }
        String tarPath=(parts.length==3)? toAbsolutePath(CommandController.getCurrentPath(),parts[2]):CommandController.getCurrentPath();
        if(!new File(tarPath).isDirectory()){
            System.out.println(AnsiColor.RED+"目标路径必须为目录"+ AnsiColor.RESET);
            return;
        }
        String outputPath= constructOutputPath(oriPath,tarPath, ComType.COPY);
        logger.info("依据目的路径，构造输出路径前的tarPath是"+outputPath);
        String result = new CopyFunc().func(oriPath,outputPath);
        System.out.println(AnsiColor.BLUE+"指令执行情况："+ AnsiColor.GREEN+result+ AnsiColor.RESET);
    }
    public static void handleCat(String[] filePath) throws IOException {
        assert (filePath.length == 2):"参数过多";
        String tarPath = toAbsolutePath(CommandController.getCurrentPath(),filePath[1]);
        if(!new File(tarPath).exists()){
            System.out.println(AnsiColor.RED+"文件不存在"+ AnsiColor.RESET);
            return;
        }
        if(!new File(tarPath).isFile()){
            System.out.println(AnsiColor.RED+"目标路径必须为文件"+ AnsiColor.RESET);
            return;
        }
        String result = new CatFunc().func(tarPath);
    }
    public static void handleCreate(String[] parts,int type){
        if(parts.length < 2){
            System.out.println(AnsiColor.RED+"需指定创建文件路径"+ AnsiColor.RESET);
            return;
        }
        if(parts.length>2){
            System.out.println(AnsiColor.RED+"参数过多"+ AnsiColor.RESET);
            return;
        }
        String oriPath= toAbsolutePath(CommandController.getCurrentPath(),parts[1]);
        if(new File(oriPath).exists()){
            System.out.println(AnsiColor.RED+"文件已存在"+ AnsiColor.RESET);
            return;
        }
        logger.info("依据目的路径，构造输出路径前的tarPath是"+oriPath);
        String result=new CreateFunc().func(oriPath,type);
    }
    public static void handleDelete(String[] parts){
        String tarpath="";
        if(parts.length==2){
            tarpath= toAbsolutePath(CommandController.getCurrentPath(),parts[1]);
        }
        if(parts.length==3){
            tarpath= toAbsolutePath(CommandController.getCurrentPath(),parts[2]);
        }
        if(!new File(tarpath).exists()){
            System.out.println(AnsiColor.RED+"文件不存在"+ AnsiColor.RESET);
            return;
        }
        String result=new DeleteFunc().func(tarpath);
    }
    public static void handleZip(String[] parts) {
        if(parts.length < 2){
            System.out.println(AnsiColor.RED+"需指定压缩文件或目录"+ AnsiColor.RESET);
            return;
        }
        if(parts.length>3){
            System.out.println(AnsiColor.RED+"参数过多"+ AnsiColor.RESET);
            return;
        }
        String oriPath= toAbsolutePath(CommandController.getCurrentPath(),parts[1]);
        if(!new File(oriPath).exists()){
            System.out.println(AnsiColor.RED+"源文件或目录不存在"+ AnsiColor.RESET);
            return;
        }
        String tarPath=(parts.length==3)? toAbsolutePath(CommandController.getCurrentPath(),parts[2]):CommandController.getCurrentPath();
        logger.info("依据目的路径，构造输出路径前的tarPath是"+tarPath);
        String result = new ZipFunc().func(parts[0],oriPath,tarPath);
        System.out.println(AnsiColor.BLUE+"指令执行情况："+ AnsiColor.GREEN+result+ AnsiColor.RESET);
    }
    public static void handleLs(String[]parts) {
        String tarPath=CommandController.getCurrentPath();
        logger.info("依据目的路径，构造输出路径前的tarPath是"+tarPath+"指令长度为"+parts.length);
         if(parts.length>2){
            System.out.println(AnsiColor.RED+"参数过多"+ AnsiColor.RESET);
            return;
        }else if(parts.length==2){
            tarPath=toAbsolutePath(CommandController.getCurrentPath(),parts[1]);
            if(!new File(tarPath).exists()){
                System.out.println(AnsiColor.RED+"目录不存在"+ AnsiColor.RESET);
                return;
            }
            if(!new File(tarPath).isDirectory()){
                System.out.println(AnsiColor.RED+"目标路径必须为目录"+ AnsiColor.RESET);
                return;
            }
        }
        String result = new ShowFunc().func(tarPath);
        System.out.println(AnsiColor.BLUE+"指令执行情况："+ AnsiColor.GREEN+result+ AnsiColor.RESET);
    }
    public static void handleDir() {
        try {
            // 使用 cmd.exe 来执行 dir 命令
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "dir", CommandController.getCurrentPath());
            processBuilder.directory(new java.io.File(CommandController.getCurrentPath()));
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(),  "GBK"));
            displayManager.MainShow(()->{
                String line;
                // 循环读取并打印ls命令的输出结果
                while (true) {
                    try {
                        if ((line = reader.readLine()) == null) break;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(line);
                }
            });
            // 关闭输入流
            reader.close();
            // 等待命令执行完成，并获取退出码
            int exitCode = process.waitFor();
            // 如果退出码不为0，表示命令执行出错
            if (exitCode != 0) {
                System.out.println("Error executing ls command");
            }
        } catch (IOException e) {
            // 如果抛出IOException，将其转换为RuntimeException重新抛出
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            // 如果抛出InterruptedException，将其转换为RuntimeException重新抛出
            throw new RuntimeException(e);
        }
    }
}
