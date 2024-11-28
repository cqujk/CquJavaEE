package org.web2.utils;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
public class CookieUtil {
    public static Optional<Cookie> getCookieByName(HttpServletRequest request, String name) {
        if (request == null || name == null) {
            throw new IllegalArgumentException("Request and name cannot be null");
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                System.out.printf("web1 get cookie name "+cookie.getName()+" and cookie "+ cookie);
                if (name.equals(cookie.getName())) {
                    return Optional.of(cookie);
                }
            }
        }

        return Optional.empty();
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
