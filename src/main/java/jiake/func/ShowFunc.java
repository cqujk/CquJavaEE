package jiake.func;

import jiake.global.DisplayManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import static jiake.global.DisplayManager.waitForUserInput;
import static jiake.tool.AnsiColor.*;

public class ShowFunc extends AbstractFunc {
    Scanner scanner = new Scanner(System.in);
    private static final Logger logger= LogManager.getLogger(ShowFunc.class);
    @Override
    public <T, R> R func(T... args) {
        File currentDir = new File((String) args[0]);
        File[] files = currentDir.listFiles();
        if (files == null) {
            System.out.println(RED + "当前文件夹为空" + RESET);
            return (R) "指令执行成功";
        }

        DisplayManager.getInstance().MainShow(() -> {
            System.out.println(BLUE + "1." + YELLOW + "文件名称" + RESET);
            System.out.println(BLUE + "2." + YELLOW + "文件大小" + RESET);
            System.out.println(BLUE + "3." + YELLOW + "文件类型" + RESET);
            System.out.println(BLUE + "4." + YELLOW + "文件日期" + RESET);
            System.out.println(PURPLE + "是否进行筛选？" + RESET);
            String choice = scanner.nextLine();
            int filterFlag=0;
             if(!choice.isEmpty()){
                 filterFlag=Integer.parseInt(choice);
             }
            String filter = "";
            switch (filterFlag) {
                case 1:
                    System.out.println(PURPLE + "请输入目标文件的名称或所含字符" + RESET);
                    break;
                case 2:
                    System.out.println(PURPLE + "请输入目标文件的大小在多少以上（需附带单位B,KB,MB,GB)" + RESET);
                    break;
                case 3:
                    System.out.println(PURPLE + "请输入目标文件的后缀名" + RESET);
                    break;
                case 4:
                    System.out.println(PURPLE + "请输入目标文件的日期（格式为：yyyy-MM-dd）" + RESET);
                    break;
                default:
                    System.out.println(PURPLE + "未选择筛选条件" + RESET);
            }
            filter = scanner.nextLine();

            List<File> fileList = new ArrayList<>();
            for (File file : files) {
                if (matchesFilter(file, filterFlag, filter)) {
                    fileList.add(file);
                }
            }

            System.out.println(BLUE + "1." + YELLOW + "文件名称" + RESET);
            System.out.println(BLUE + "2." + YELLOW + "文件大小" + RESET);
            System.out.println(BLUE + "3." + YELLOW + "文件日期" + RESET);
            System.out.println(PURPLE + "是否对结果进行排序？" + RESET);
            String sortChoice = scanner.nextLine();
            int sortFlag =0;
            if(!sortChoice.isEmpty()){
                sortFlag= Integer.parseInt(sortChoice);
            }
            // 排序
            switch (sortFlag) {
                case 1:
                    Collections.sort(fileList, Comparator.comparing(File::getName));
                    break;
                case 2:
                    Collections.sort(fileList, Comparator.comparingLong(File::length));
                    break;
                case 3:
                    Collections.sort(fileList, Comparator.comparingLong(file -> file.lastModified()));
                    break;
                default:
                    // 默认按名称排序
                    Collections.sort(fileList, Comparator.comparing(File::getName));
                    break;
            }
            // 显示文件列表
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.printf("| %-20s%-15s%-18s %-24s |%n", CYAN + "类型", "最近修改日期", "文件大小（B）", "文件名称" + RESET);
            for (File file : fileList) {
                BasicFileAttributes attrs = null;
                try {
                    attrs = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String type = file.isDirectory() ? "DIR" : "FILE";
                long size = file.length();
                String lastModified = dateFormat.format(file.lastModified());
                System.out.printf("| %-20s%-32s%-18d%-30s|%n", GREEN + type + RESET, lastModified + YELLOW, size, RESET + file.getName());
            }
            waitForUserInput();
        });
        return (R) "指令执行成功";
    }

    private boolean matchesFilter(File file, int filterFlag, String filter) {
        switch (filterFlag) {
            case 1:
                return file.getName().contains(filter);
            case 2:
                try {
                    long targetSize = parseSize(filter);
                    return file.length() >= targetSize;
                } catch (NumberFormatException e) {
                    System.out.println(RED + "无效的文件大小格式" + RESET);
                    return false;
                }
            case 3:
                return file.getName().endsWith("." + filter);
            case 4:
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    long targetDate = dateFormat.parse(filter).getTime();
                    return file.lastModified() >= targetDate && file.lastModified() < targetDate + 24 * 60 * 60 * 1000;
                } catch (ParseException e) {
                    System.out.println(RED + "无效的日期格式" + RESET);
                    return false;
                }
            default:
                return true; // 不进行筛选
        }
    }

    private long parseSize(String sizeStr) {
        sizeStr = sizeStr.trim().toUpperCase();
        logger.info("此时解析到的大小为 " + sizeStr);

        // 检查字符串是否为空或长度不足
        if (sizeStr.length() < 2) {
            throw new NumberFormatException("无效的文件大小格式");
        }

        // 获取单位部分
        String unit = sizeStr.substring(sizeStr.length() - 2).trim();
        // 获取数值部分
        String numberPart = sizeStr.substring(0, sizeStr.length() - 2).trim();

        try {
            long number = Long.parseLong(numberPart);
            switch (unit) {
                case "B":
                    return number;
                case "KB":
                    return number * 1024;
                case "MB":
                    return number * 1024 * 1024;
                case "GB":
                    return number * 1024 * 1024 * 1024;
                default:
                    throw new NumberFormatException("无效的文件大小单位");
            }
        } catch (NumberFormatException e) {
            throw new NumberFormatException("无效的文件大小格式: " + e.getMessage());
        }
    }

}
