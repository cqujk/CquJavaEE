package org.sso;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.file.Files;
import java.nio.file.Paths;

public class KeyGenerator {

    private static final int KEY_SIZE = 512 / 8; // 512 bits = 64 bytes

    public static void main(String[] args) {
        try {
            // 生成随机密钥
            SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
            // 将密钥保存到文件
            String keyFilePath = "D:\\.Project\\JAVA\\exp3\\sso\\src\\main\\resources\\keyfile.key";
            Files.write(Paths.get(keyFilePath),secretKey.getEncoded());
            System.out.println("the key has saved in: " + keyFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
