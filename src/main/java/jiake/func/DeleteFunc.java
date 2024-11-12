package jiake.func;

import java.io.File;

public class DeleteFunc extends AbstractFunc{
    public <T, R> R func(T... args)
    {
        File file=new File(String.valueOf(args[0]));
        if(deleteDirectoryRecursively(file)){
            return (R) "指令执行成功";
        }else{
            return (R) ("指令执行失败");
        }
    }
    private boolean deleteDirectoryRecursively(File dir) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        if (!deleteDirectoryRecursively(file)) {
                            return false;
                        }
                    } else {
                        if (!file.delete()) {
                            return false;
                        }
                    }
                }
            }
        }
        return dir.delete();
    }
    public String deleteDirectory(String path) {
        File dir = new File(path);
        if (dir.exists()) {
            if (dir.isDirectory()) {
                if (deleteDirectoryRecursively(dir)) {
                    return "目录删除成功";
                } else {
                    return "目录删除失败";
                }
            } else {
                return "指定路径不是一个目录";
            }
        } else {
            return "目录不存在";
        }
    }
    public String deleteFile(File file) {
        if (file.exists()) {
            if (file.delete()) {
                return "文件删除成功";
            } else {
                return "文件删除失败";
            }
        } else {
            return "文件不存在";
        }
    }
}
