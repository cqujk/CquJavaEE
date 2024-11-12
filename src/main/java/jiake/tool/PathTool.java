package jiake.tool;

import jiake.global.ComType;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.regex.Pattern;

public class PathTool {
    // 定义非法字符的正则表达式
    private static final Pattern ILLEGAL_CHARS_PATTERN = Pattern.compile("[<>\"/|?*]");
    private static Optional<String> findFirstIllegalChar(String path) {
        return ILLEGAL_CHARS_PATTERN.matcher(path).results()
                .map(matchResult -> path.substring(matchResult.start(), matchResult.end()))
                .findFirst();
    }
    //将路径转为绝对路径
    public static String toAbsolutePath(String currentParh, String path){
        // 检查路径是否包含非法字符
        Optional<String> illegalChar = findFirstIllegalChar(path);
        if (illegalChar.isPresent()) {
            throw new InvalidPathException(path, "路径包含非法字符: " + illegalChar.get());
        }
        //先判断是不是一个绝对路径
        if (Path.of(path).isAbsolute()) {
            return path;
        }else {
            switch (path) {
                case ".."://当前路径的上一层
                    return currentParh.substring(0, currentParh.lastIndexOf("\\"));
                case "~"://用户的根目录
                    return System.getProperty("user.home");
                case ".":
                    return currentParh;
                default://从currentPath下的相对路径转为绝对路径
                    return currentParh + "\\" + path;
            }
        }
    }
    public static String constructOutputPath(String oriPath, String tarPath, ComType kind) {
        //要求目标路径必须是目录
        assert( new File(tarPath).isDirectory()):"目标路径必须是目录";
        File oriFile = new File(oriPath);
        String fileName = oriFile.getName();
        String fileExtension = "";
        String baseName = fileName;
        //若为文件、则去除后缀名，并取得后缀名
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            fileExtension = fileName.substring(dotIndex);
            baseName = fileName.substring(0, dotIndex);
        }
        //判断是否存在，若不存在，则创建
        if(!new File(tarPath).exists()) {
            if(!new File(tarPath).mkdirs()){
                System.out.println(AnsiColor.RED+"无法创建目标目录，请检查目标路径是否正确"+ AnsiColor.RESET);
                return null;
            };
        }
        String LastName="";
        //依据指令类型，选择生成文件的最后名,
        // 若有.后缀名就是文件，不然就是目录
        switch(kind){
            case COPY://为源文件相同的后缀名(或没有）
                LastName=fileExtension;
                break;
            case ZIP:
                LastName=".zip";
                break;
            case UNZIP://解压出目录，与压缩文件名相同
                break;
            case GENKEY:
                LastName=".key";
                break;
            case GENPUB:
                LastName=".pub";
                break;
            case ENCRY:
                LastName=".enc";

        }
        String newFileName=baseName+LastName;
        int i = 1;
        while (new File(tarPath, newFileName).exists()){
            newFileName = baseName + "(" + i + ")" + LastName;
            i++;
        }
        return new File(tarPath, newFileName).getAbsolutePath();
    }
}
