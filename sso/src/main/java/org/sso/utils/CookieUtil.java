package org.sso.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
    // cookie过期时间,1 小时
    private static final long EXPIRATION_TIME = 3600000L;
    public static Cookie generateCookie(String token){

        // 创建 Cookie
        Cookie tokenCookie = new Cookie("token", token);
        tokenCookie.setPath("/");
        //tokenCookie.setHttpOnly(true);
        tokenCookie.setSecure(true);
        //tokenCookie.setDomain("127.0.0.1"); // 设置域，使其在多个端口之间有效
        tokenCookie.setMaxAge((int) (EXPIRATION_TIME / 1000)); // 设置 Cookie 的时间

        System.out.println("Cookie created: " + token);
        return tokenCookie;
    }
    public static void clearCookies(HttpServletResponse response) {
        Cookie tokenCookie = new Cookie("token", "");
        tokenCookie.setPath("/");
        tokenCookie.setHttpOnly(true);
        tokenCookie.setMaxAge(0);
//        Cookie sessionIdCookie = new Cookie("JSESSIONID", "");
//        sessionIdCookie.setPath("/");
//        sessionIdCookie.setHttpOnly(true);
//        sessionIdCookie.setMaxAge(0);
        response.addCookie(tokenCookie);
//        response.addCookie(sessionIdCookie);
    }
}

