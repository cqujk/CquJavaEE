package org.sso.filter;

import org.sso.utils.TokenUtils;

import javax.servlet.annotation.WebFilter;
import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;

@WebFilter("/*")
public class TokenFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        String requestURI = httpRequest.getRequestURI();
        System.out.println("filter get  "+requestURI);
        if(session !=null && session.getAttribute("isLoggedIn")!=null){
            if(requestURI.endsWith("index")){
                httpResponse.sendRedirect("home");
            }else{
                chain.doFilter(request, response);

            }
        }else{
            if(requestURI.endsWith("index.jsp")||(requestURI.endsWith("login"))){//直接放行，防止死循环
                chain.doFilter(request,response);
                System.out.println("filter pass because the token is null or invalid,what's more, target resource is"+requestURI);
                return;
            }
            if(requestURI.endsWith("checkToken")){
                chain.doFilter(request,response);
                System.out.println("filter: there may be a new web that has no cookie to check token");
                return;
            }
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
}
