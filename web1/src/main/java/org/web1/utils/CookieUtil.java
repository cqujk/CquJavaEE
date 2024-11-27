package org.web1.utils;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
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
}
