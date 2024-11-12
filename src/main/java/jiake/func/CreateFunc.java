package jiake.func;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CreateFunc extends AbstractFunc{
    private static Logger logger= LogManager.getLogger(CreateFunc.class);
    @Override
    public <T, R> R func(T... args) {
        String path = String.valueOf(args[0]);
        logger.info("接收到的参数是"+path);
        //给path每个分隔符替换为系统的分隔符
        path = path.replace("\\", File.separator);
        logger.info("替换后的参数是"+path);
//        if (!path.endsWith(File.separator)) {
//            path += File.separator;
//        }
        Path Newpath = Paths.get(String.valueOf(args[0]));
        File file = new File(path);
        //判断是否是目录
        logger.info("运行到这步了，接收到的路径是"+path);
            // 确保路径以文件分隔符结尾，如果是目录
            if(args[1].equals(0)) {
                File dir = new File(path);
                logger.info("此时的路径为："+path);
                if (dir.exists() || dir.mkdirs()) {
                    logger.info("目录创建成功：{}", path);
                    return (R) "指令执行成功";
                } else {
                    logger.error("目录创建失败：{}", path);
                    return (R) "创建目录失败";
                }
            } else {
//                File parentDir = file.getParentFile();
//                if (parentDir != null && !parentDir.exists() && !parentDir.mkdirs()) {
//                    logger.error("创建父目录失败：{}", parentDir.getAbsolutePath());
//                    return (R) "创建父目录失败";
//                }
                try {
//                    file.createNewFile();
//                    if (file.createNewFile()) {
//                        logger.info("文件创建成功：{}", path);
//                        logger.info(file.getName()+"文件，存在"+file.exists());
//                        logger.info("文件大小"+file.length());
//                        logger.info("可读情况"+file.canRead());
//                        logger.info("可写情况"+file.canWrite());
//                        logger.info("是否隐藏"+file.isHidden());
//                        FileWriter fw = new FileWriter(file);//创建文件写入者
//                        fw.write("这是由JAVA第二次实验创建的文件");//写入数据
//                        fw.close();//关闭写入者
                    logger.info("createFile要执行了");
                    Files.createFile(Newpath);
                    logger.info("文件创建成功：{}", Newpath);
                    return (R) "指令执行成功";
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
//            if (file.isDirectory()) {
//                logger.info("运行到这里了，准备创建文件夹");
//                if (file.mkdirs()) {
//                    return (R) "指令执行成功";
//                } else {
//                    return (R) ("创建文件夹失败");
//                }
//            } else {
//                if (file.createNewFile()) {
//                    logger.info("运行到这里了，创建文件"+file);
//                    return (R) "指令执行成功";
//                } else {
//                    return (R) ("创建文件失败");
//                }

    }
}
