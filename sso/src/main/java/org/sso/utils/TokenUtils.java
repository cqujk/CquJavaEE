package org.sso.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.crypto.SecretKey;
import java.util.Date;

public class TokenUtils {
    private static final SecretKey SECRET;
    private static final long TOKEN_EXPIRATION_TIME = 3600 * 1000; // 1小时;
    // 发行者
    private static final String ISSUER = "20220669JiaKe";

    static {
        try {
            SECRET = KeyUtil.readKeyFromFile("D:\\.Project\\JAVA\\exp3\\sso\\src\\main\\resources\\keyfile.key");
            System.out.println("has get secret key from file");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private static final long EXPIRATION_TIME= TOKEN_EXPIRATION_TIME;
    // private static final String ISSURE="JiaKe20220669";
    public static String generateToken(String userId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiration = now.plusSeconds(TOKEN_EXPIRATION_TIME / 1000);
        System.out.println("expiration time is "+expiration+" and Date "+Date.from(now.atZone(ZoneId.systemDefault()).toInstant()));

        System.out.println("current time is "+now+" and Date "+Date.from(now.atZone(ZoneId.systemDefault()).toInstant()));
        return Jwts.builder()
                .setSubject(userId)
                .setIssuer(ISSUER) // 发行者
                .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant())) // 发行时间
                .setExpiration(Date.from(expiration.atZone(ZoneId.systemDefault()).toInstant())) // 过期时间
                .signWith(SECRET,SignatureAlgorithm.HS512)
                .compact();
    }
    public static String getUserIdFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public static boolean validateToken(String token)  throws Exception{
        try{
            Claims claims=Jwts.parserBuilder()
                    .setSigningKey(SECRET)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            // 验证过期时间
            Date expiration = claims.getExpiration();
            Date now=Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
            System.out.println("when check token expiration time is "+expiration+" and Date "+now);
            if (expiration.before(now)) {
                throw new Exception("Token has expired.");
            }
            // 验证发行者
            String issuer = claims.getIssuer();
            if (!ISSUER.equals(issuer)) {
                throw new Exception("Invalid issuer.");
            }
            return true;
        }catch(Exception e){
            System.out.println("when check the token, the result is false!!!!!!!!"+ e.getMessage());
            return false;
        }
    }

}