package jiake.func;

import jiake.global.DisplayManager;
import jiake.tool.AnsiColor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Objects;

public class CopyFunc extends AbstractFunc{
    private final DisplayManager displayManager =DisplayManager.getInstance();
    private static final Logger logger= LogManager.getLogger(CopyFunc.class);
    @SafeVarargs
    @Override
    public final <T, R> R func(T... args) {
        logger.info("copy中收到的指令是"+ Arrays.toString(args));
        assert (args.length==2):"copy指令参数个数错误";
        File oriFile = new File(String.valueOf(args[0]));
        File tarFile = new File(String.valueOf(args[1]));
        if(oriFile.isDirectory()){
            copyDirectory(oriFile,tarFile);
        }else{
            copyFile(oriFile, tarFile);
        }
        return (R) "SUCCESS";
    }
    private void copyDirectory(File srcDir, File destDir) {
        if (!destDir.exists()) {
            destDir.mkdirs();
        }

        for (File srcFile : Objects.requireNonNull(srcDir.listFiles())) {
            File destFile = new File(destDir, srcFile.getName());
            if (srcFile.isDirectory()) {
                copyDirectory(srcFile, destFile);
            } else {
                copyFile(srcFile, destFile);
            }
        }
    }
    private void copyFile(File srcFile, File destFile) {
        try {
            Files.copy(srcFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println(AnsiColor.GREEN + "文件复制成功" + AnsiColor.RESET);
        } catch (IOException e) {
            System.out.println(AnsiColor.RED + "文件复制失败: " + e.getMessage() + AnsiColor.RESET);
        }
    }
}
