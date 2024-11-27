package org.sso.utils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.file.Files;
import java.nio.file.Paths;

public class KeyUtil {

    private static final int KEY_SIZE = 512 / 8; // 512 bits = 64 bytes
    /**
     * 从文件中读取 HS512 密钥
     * @param keyFilePath 密钥文件路径
     * @return 密钥对象
     * @throws Exception 如果读取失败
     */
    public static SecretKey readKeyFromFile(String keyFilePath) throws Exception {
        byte[] keyBytes = Files.readAllBytes(Paths.get(keyFilePath));
        if (keyBytes.length != KEY_SIZE) {
            throw new IllegalArgumentException("Maybe Some error!The length is not match");
        }
        return new SecretKeySpec(keyBytes, "HmacSHA512");
    }
}