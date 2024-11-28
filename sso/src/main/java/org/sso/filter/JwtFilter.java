package org.sso.filter;

import org.sso.utils.TokenUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class JwtFilter  implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        String requestURI = httpRequest.getRequestURI();
        System.out.println("filter get  " + requestURI);
        // 获取所有 Cookie
        Cookie[] cookies = httpRequest.getCookies();
        if (cookies != null) {
            // 查找名为 "token" 的 Cookie
            Optional<Cookie> tokenCookie = Arrays.stream(cookies)
                    .filter(cookie -> "token".equals(cookie.getName()))
                    .findFirst();
            System.out.println("sso filter get cookie "+tokenCookie);
            if (tokenCookie.isPresent()) {
                String token = tokenCookie.get().getValue();
                System.out.println("filter get token "+token);
                try {
                    boolean isValidToken = TokenUtils.validateToken(token);
                    System.out.println("the token check result"+ isValidToken);
                    if (isValidToken) {
                        System.out.println("filter pass!!!!!!!!!!!");
                        chain.doFilter(request, response);
                    }else{
                        httpResponse.sendRedirect("index.jsp");
                    }
                } catch (Exception e) {
                    httpResponse.sendRedirect("index.jsp");
                }
            }else{
                if(requestURI.endsWith("home")){
                    httpResponse.sendRedirect("home.jsp");
                }else{
                    httpResponse.sendRedirect("index.jsp");
                }
            }
        }else{
            System.out.println("DEBUG !!!! the cookie is null");
            httpResponse.sendRedirect("index.jsp");
        }
    }
}
