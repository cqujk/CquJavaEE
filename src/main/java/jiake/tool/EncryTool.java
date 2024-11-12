package jiake.tool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.util.Base64;

public class EncryTool {
    static Logger logger= LogManager.getLogger(EncryTool.class);
    // 生成对称密钥
    public static SecretKey generateSymmetricKey(String algorithm) throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance(algorithm);
        if ("DES".equalsIgnoreCase(algorithm)) {
            keyGen.init(56); // 确保 DES 密钥长度为 56 位
        } else {
            // 其他算法的默认密钥长度
            keyGen.init(128); // 例如 AES 默认 128 位
        }
        return keyGen.generateKey();
    }

    // 生成非对称密钥对
    public static KeyPair generateAsymmetricKeyPair(String algorithm) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(algorithm);
        keyPairGen.initialize(2048); // 设置密钥长度
        logger.info("生成公钥时，KeyPairGen已完成初始化");
        return keyPairGen.generateKeyPair();
    }
    public static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

    // 保存密钥到文件
    public static void saveKeyToFile(SecretKey key, String keyFilePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(keyFilePath)) {
            fos.write(key.getEncoded());
        }
    }


    // 从文件读取密钥
    public static SecretKey readKeyFromFile(String keyFilePath, String algorithm) throws IOException, NoSuchAlgorithmException {
        byte[] encodedKey = Files.readAllBytes(new File(keyFilePath).toPath());
        return new SecretKeySpec(encodedKey, algorithm);
    }


    // 生成初始化向量 (IV)
    public static IvParameterSpec generateIv(String algorithm) {
        return switch (algorithm.toUpperCase()) {
            case "AES" -> generateIv(16);
            case "BLOWFISH", "DES" -> generateIv(8);
            default -> throw new IllegalArgumentException("不支持的算法: " + algorithm);
        };
    }
    // 生成指定长度的初始化向量 (IV)
    public static IvParameterSpec generateIv(int length) {
        byte[] iv = new byte[length];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }
    public static int getIvSize(String algorithm) {
        switch (algorithm.toUpperCase()) {
            case "AES":
                return 16;
            case "BLOWFISH":
            case "DES":
                return 8;
            default:
                throw new IllegalArgumentException("不支持的算法: " + algorithm);
        }
    }
//    // 生成随机密钥（十六进制字符串形式）
//    public static String generateRandomKey() {
//        SecureRandom random = new SecureRandom();
//        byte[] keyBytes = new byte[KEY_SIZE / 8];
//        random.nextBytes(keyBytes);
//        return bytesToHex(keyBytes);
//    }
//    // 保存随机密钥到文件
//    public static void saveRandomKeyToFile(String key, String keyFilePath) throws IOException {
//        try (FileWriter writer = new FileWriter(keyFilePath)) {
//            writer.write(key);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    // 从文件读取随机密钥
//    public static String readRandomKeyFromFile(String keyFilePath) throws IOException {
//        try (BufferedReader reader = new BufferedReader(new FileReader(keyFilePath))) {
//            return reader.readLine();
//        }
//    }
//    public static KeyPair generateKeyPair(String ALGORITHM) throws Exception {
//        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
//        keyGen.initialize(2048);
//        return keyGen.generateKeyPair();
//    }
    public static void saveKeyPairToFile(KeyPair keyPair, String publicKeyFilePath, String privateKeyFilePath) throws Exception {
        try (FileOutputStream fos = new FileOutputStream(publicKeyFilePath)) {
            fos.write(keyPair.getPublic().getEncoded());
        }
        try (FileOutputStream fos = new FileOutputStream(privateKeyFilePath)) {
            fos.write(keyPair.getPrivate().getEncoded());
        }
    }
    public static PublicKey readPublicKeyFromFile(String ALGORITHM,String keyFilePath) throws Exception {
        byte[] encodedKey = Files.readAllBytes(Paths.get(keyFilePath));
        return loadPublicKey(ALGORITHM, encodedKey);
    }
    public static PrivateKey readPrivateKeyFromFile(String ALGORITHM,String keyFilePath) throws Exception {
        byte[] encodedKey = Files.readAllBytes(Paths.get(keyFilePath));
        return loadPrivateKey(ALGORITHM, encodedKey);
    }
    public static PublicKey loadPublicKey(String ALGORITHM,byte[] encodedKey) throws Exception {
        String pemKey = new String(encodedKey);
        if (pemKey.contains("-----BEGIN PUBLIC KEY-----")) {
            pemKey = pemKey.replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s+", "");
            encodedKey = Base64.getDecoder().decode(pemKey);
        }
        return java.security.KeyFactory.getInstance(ALGORITHM).generatePublic(new java.security.spec.X509EncodedKeySpec(encodedKey));
    }

    public static PrivateKey loadPrivateKey(String ALGORITHM,byte[] encodedKey) throws Exception {
        // 如果密钥是 PEM 格式，需要去掉开头和结尾的标记，并进行 Base64 解码
        String pemKey = new String(encodedKey);
        if (pemKey.contains("-----BEGIN PRIVATE KEY-----")) {
            pemKey = pemKey.replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s+", "");
            encodedKey = Base64.getDecoder().decode(pemKey);
        }
        return java.security.KeyFactory.getInstance(ALGORITHM).generatePrivate(new java.security.spec.PKCS8EncodedKeySpec(encodedKey));
    }
}
