package org.sso.servlet;

import org.sso.service.UserService;
import org.sso.utils.CookieUtil;
import org.sso.utils.KeyUtil;
import org.sso.utils.TokenUtils;

import javax.crypto.SecretKey;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

//@WebServlet(name = "loginServlet", value = "/login")
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long EXPIRATION_TIME = 3600000L;
    private final UserService userService= new UserService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        if(userService.validateUser(userId, password)){
            String token = TokenUtils.generateToken(userId);
            // 删除旧的 token Cookie
            Cookie oldTokenCookie = new Cookie("token", "");
            oldTokenCookie.setMaxAge(0); // 设置过期时间为 0，表示立即删除
            oldTokenCookie.setPath("/"); // 确保路径匹配
            response.addCookie(oldTokenCookie);
            // 创建 Cookie
//            Cookie tokenCookie = CookieUtil.generateCookie(token);
            // 手动设置 Set-Cookie 头
            StringBuilder cookieHeader = new StringBuilder();
            cookieHeader.append("token=").append(token).append("; Path=/; Secure; SameSite=None; Max-Age=").append(EXPIRATION_TIME / 1000);
            response.setHeader("Set-Cookie", cookieHeader.toString());
//            // 添加 Cookie 到响应
//            response.addCookie(tokenCookie);
            // 设置会话属性，表示请求已经通过了登录验证
            HttpSession session = request.getSession();
            session.setAttribute("isLoggedIn", true);
            response.sendRedirect("home");
        }else{
            response.sendRedirect("index.jsp");
        }
    }
}