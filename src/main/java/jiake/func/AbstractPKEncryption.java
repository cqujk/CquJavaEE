package jiake.func;

import javax.crypto.Cipher;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.PublicKey;

import static jiake.tool.EncryTool.readPrivateKeyFromFile;
import static jiake.tool.EncryTool.readPublicKeyFromFile;


public abstract class AbstractPKEncryption implements Encryption{
    String ALGORITHM;
    String TRANSFORMATION;
    public AbstractPKEncryption(String algorithm, String transformation) {
        this.ALGORITHM = algorithm;
        this.TRANSFORMATION = transformation;
    }/**
     * 加密文件内容
     *
     * @param inputFilePath 输入文件的路径
     * @param outputFilePath 加密后输出文件的路径
     * @param pubKeyFilePath 公钥文件的路径
     * @throws Exception 如果加密过程中发生错误
     */
    @Override
    public void encrypt(String inputFilePath, String outputFilePath, String pubKeyFilePath) throws Exception {
        // 读取公钥
        PublicKey publicKey = readPublicKeyFromFile(ALGORITHM,pubKeyFilePath);
        // 创建Cipher对象用于加密
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        // 初始化Cipher对象为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        Path path = Paths.get(inputFilePath);
        byte[] inputBytes = Files.readAllBytes(path);// 读取输入文件的所有字节
        // 加密输入文件的字节
        byte[] outputBytes = cipher.doFinal(inputBytes);
        // 获取原始文件名和后缀名
        String originalFileName = path.getFileName().toString();
        // 将原始文件名转换为字节
        byte[] fileNameBytes = originalFileName.getBytes();
        // 使用FileOutputStream写入加密后的文件
        try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
            // 写入文件名长度
            fos.write(fileNameBytes.length);
            // 写入文件名
            fos.write(fileNameBytes);
            // 写入加密后的文件内容
            fos.write(outputBytes);
        }
    }

    /**
     * 解密文件内容
     *
     * @param inputFilePath 输入文件的路径
     * @param outputFilePath 解密后输出文件的路径
     * @param keyFilePath 私钥文件的路径
     * @throws Exception 如果解密过程中发生错误
     */
    @Override
    public void decrypt(String inputFilePath, String outputFilePath, String keyFilePath) throws Exception {
        // 读取私钥
        PrivateKey privateKey = readPrivateKeyFromFile(ALGORITHM,keyFilePath);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        // 初始化Cipher对象为解密模式
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        try (FileInputStream fis = new FileInputStream(inputFilePath)) {
            // 读取文件名长度
            int fileNameLength = fis.read();
            // 根据文件名长度创建字节数组
            byte[] fileNameBytes = new byte[fileNameLength];
            fis.read(fileNameBytes);
            // 将文件名字节数组转换为字符串
            String originalFileName = new String(fileNameBytes);
            // 创建输入字节数组以读取剩余文件内容
            byte[] inputBytes = new byte[fis.available()];
            // 读取加密后的文件内容
            fis.read(inputBytes);
            // 解密文件内容
            byte[] outputBytes = cipher.doFinal(inputBytes);
            // 构建输出文件路径
            String outputFilePathWithOriginalName = Paths.get(outputFilePath).resolve(originalFileName).toString();
            try (FileOutputStream fos = new FileOutputStream(outputFilePathWithOriginalName)) {
                // 写入解密后的文件内容
                fos.write(outputBytes);
            }
        }
    }

}
