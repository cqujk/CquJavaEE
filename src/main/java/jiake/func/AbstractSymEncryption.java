package jiake.func;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.PublicKey;

import static jiake.tool.EncryTool.*;


public class AbstractSymEncryption implements Encryption {
    String ALGORITHM;
    String TRANSFORMATION;
    private static final Logger logger= LogManager.getLogger(AbstractSymEncryption.class);

    public AbstractSymEncryption(String algorithm, String transformation) {
        this.ALGORITHM = algorithm;
        this.TRANSFORMATION = transformation;
    }
    @Override
    public void encrypt(String inputFilePath, String outputFilePath, String keyFilePath) throws Exception {
        // 读取密钥
        SecretKey keyFromFile = readKeyFromFile(keyFilePath,ALGORITHM);
        logger.debug("在加密时，读取的密钥是"+keyFromFile);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        SecretKeySpec secretKeySpec = new SecretKeySpec( keyFromFile.getEncoded(), ALGORITHM);
        IvParameterSpec ivParameterSpec = generateIv(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

        Path path = Paths.get(inputFilePath);
        logger.debug("在加密时，读取的文件路径是"+inputFilePath);
        byte[] inputBytes = Files.readAllBytes(path);
        byte[] outputBytes = cipher.doFinal(inputBytes);
        // 获取原始文件名和后缀名
        String originalFileName = path.getFileName().toString();
        logger.debug("加密时，写入的后缀名是"+originalFileName);
        byte[] fileNameBytes = originalFileName.getBytes();
        try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
            fos.write(ivParameterSpec.getIV());
            fos.write(fileNameBytes.length); // 写入文件名长度
            fos.write(fileNameBytes); // 写入文件名
            fos.write(outputBytes);
        }
    }

    @Override
    public void decrypt(String inputFilePath, String outputFilePath, String keyFilePath) throws Exception {
        // 读取密钥
        SecretKey keyFromFile = readKeyFromFile(keyFilePath,ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyFromFile.getEncoded(), ALGORITHM);
        try (FileInputStream fis = new FileInputStream(inputFilePath)) {
            byte[] iv = new byte[getIvSize(ALGORITHM)];
            fis.read(iv);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            // 读取文件名长度
            int fileNameLength = fis.read();
            byte[] fileNameBytes = new byte[fileNameLength];
            fis.read(fileNameBytes);
            String originalFileName = new String(fileNameBytes);
            logger.debug("在解密时，取得的原文件名是"+originalFileName);
            // 构建输出文件路径
            String outputFilePathWithOriginalName = Paths.get(outputFilePath).resolve(originalFileName).toString();
            logger.debug("在解密时，输出的文件路径是"+outputFilePathWithOriginalName);
            Path path = Paths.get(outputFilePathWithOriginalName);
            if(!Files.exists(path)){
                Files.createFile(path);
            }else{
                int i=1;
                while (Files.exists(path)){
                    path=Paths.get(outputFilePath+"\\("+i+")"+originalFileName);
                    i++;
                }
            }
            logger.debug("在解密时，最终输出的文件路径是"+path);
            // 读取加密内容
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            byte[] encryptedBytes = baos.toByteArray();
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            try (FileOutputStream fos = new FileOutputStream(String.valueOf(path))) {
                fos.write(decryptedBytes);
            }
        }
    }
}
