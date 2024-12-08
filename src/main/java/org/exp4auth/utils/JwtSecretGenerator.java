package org.exp4auth.utils;

import java.security.SecureRandom;
import java.util.Base64;

public class JwtSecretGenerator {

    private static final int SECRET_LENGTH = 64; // 密钥长度，64字节对应512位

    public static void main(String[] args) {
        String jwtSecret = generateJwtSecret(SECRET_LENGTH);
        System.out.println("Generated JWT Secret: " + jwtSecret);
    }

    public static String generateJwtSecret(int length) {
        byte[] randomBytes = new byte[length];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }
}
