package jiake.func;


import jiake.global.DisplayManager;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static jiake.global.DisplayManager.waitForUserInput;
import static jiake.tool.AnsiColor.*;


public class CatFunc extends AbstractFunc{
    @Override
    public <T, R> R func(T... args) {
        Path path = Path.of((String) args[0]);
        try{
            if (isTextFile(path)) {
                String content = new String(Files.readAllBytes(path));
                DisplayManager.getInstance().MainShow(() -> {
                    System.out.println(BLUE+"+++++++++++++"+GREEN+"文件内容"+BLUE+"+++++++++++++"+RESET);
                    System.out.println(content);
                    System.out.println(BLUE+"+++++++++++++"+GREEN+"文件内容"+BLUE+"+++++++++++++"+RESET);
                    waitForUserInput();
                });
            } else {
                // 打开原文件进行显示
                openFileWithDefaultApp(path);
            }
        }catch (Exception e){
            System.out.println("在读取文件时出错了");
        };
        return (R)"读取文件成功";
    }
    private static void openFileWithDefaultApp(Path path) throws IOException, IOException {
        // 使用默认应用程序打开文件
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(path.toFile());
        } else {
            System.out.println("Desktop is not supported on this platform.");
        }
    }
    private static boolean isTextFile(Path path) throws IOException {
        // 检测文件是否为文本文件
        String contentType = Files.probeContentType(path);
        return contentType != null && contentType.startsWith("text");
    }
}
