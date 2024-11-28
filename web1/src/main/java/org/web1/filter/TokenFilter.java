package org.web1.filter;

import org.web1.dao.UserSessionRedis;
import org.web1.service.UserService;
import org.web1.utils.CookieUtil;

import javax.servlet.annotation.WebFilter;
import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;

import static org.web1.utils.TokenUtil.checkToken;

@WebFilter("/index")
public class TokenFilter implements Filter {
    UserSessionRedis userSessionRedis = new UserSessionRedis();
    UserService userService = new UserService();
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        System.out.println("Web1 filter get  " + httpRequest.getRequestURI());
        // 从请求中获取 token
        Optional<Cookie> tokenCookie = CookieUtil.getCookieByName(httpRequest, "token");
        System.out.println("Web1 filter get cookie "+ tokenCookie);
        if (tokenCookie.isPresent()) {
            String token = tokenCookie.get().getValue();
            System.out.println("Web1 filter get token " + token);
            // 向 SSO 服务发送 /checkToken 请求
            String ssoUrl = "http://127.0.0.1:9003/checkToken?token=" + token;
            try {
                boolean isValid = checkToken(ssoUrl);
                System.out.println("Web1 filter check result "+ isValid);
                if (isValid) {
                    Optional<Cookie>userIdCookie = CookieUtil.getCookieByName(httpRequest, "userId");
                    if (userIdCookie.isPresent()) {
                        String userId = userIdCookie.get().getValue();
                        userSessionRedis.login(userId, System.currentTimeMillis());
                        userService.insertLoginRecord(userId);
                    }
                    chain.doFilter(request, response);
                } else {
                    httpResponse.sendRedirect("http://127.0.0.1:9003/index");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            // 没有 token，重定向到登录页面
            httpResponse.sendRedirect("http://127.0.0.1:9003/index");
        }
    }
}