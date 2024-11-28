package org.sso.servlet;

import org.sso.dao.UserSessionRedis;
import org.sso.service.UserService;
import org.sso.utils.CookieUtil;
import org.sso.utils.KeyUtil;
import org.sso.utils.TokenUtils;

import javax.crypto.SecretKey;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

//@WebServlet(name = "loginServlet", value = "/login")
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long EXPIRATION_TIME = 3600000L;
    private final UserService userService= new UserService();
    private UserSessionRedis userSessionRedis = new UserSessionRedis();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        try {
            if(userService.validateUser(userId, password)){
                String token = TokenUtils.generateToken(userId);
                // 删除旧的 token Cookie
                Cookie oldTokenCookie = new Cookie("token", "");
                oldTokenCookie.setMaxAge(0); // 设置过期时间为 0，表示立即删除
                oldTokenCookie.setPath("/"); // 确保路径匹配
                response.addCookie(oldTokenCookie);
    //            // 添加 Cookie 到响应
    //            response.addCookie(tokenCookie);
                // 设置会话属性，表示请求已经通过了登录验证
                HttpSession session = request.getSession();
                session.setAttribute("userId", userId);
                session.setAttribute("isLoggedIn", true);
                userSessionRedis.login(userId, System.currentTimeMillis());
                System.out.println("LoginServlet: sessionId is :"+session.getId());
                // 创建 Cookie
                Cookie tokenCookie = CookieUtil.generateCookie(token);
                 //手动设置 Set-Cookie 头
                StringBuilder sessionIdCookieHeader = new StringBuilder();
                sessionIdCookieHeader.append("userId=").append(userId)
                        .append("; Path=/")
                        .append("; Secure")
                        .append("; Max-Age=").append(EXPIRATION_TIME / 1000)
                        .append("; SameSite=None");

                StringBuilder tokenCookieHeader = new StringBuilder();
                tokenCookieHeader.append("token=").append(token)
                        .append("; Path=/")
                        .append("; Secure")
                        .append("; Max-Age=").append(EXPIRATION_TIME / 1000)
                        .append("; SameSite=None");
                // 插入登录记录到 MySQL
                insertLoginRecord(userId);
                // 设置 Set-Cookie 头
                response.setHeader("Set-Cookie", sessionIdCookieHeader.toString());
                response.addHeader("Set-Cookie", tokenCookieHeader.toString());
                response.sendRedirect("home");
            }else{
                response.sendRedirect("index.jsp");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void insertLoginRecord(String userId) {
        System.out.println("Inserting login record for user: " + userId);
        String sql = "INSERT INTO user_login (user_id, login_time) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            //当前时间
            //System.out.println("Current time: " + System.currentTimeMillis());
            // 将时间戳转换为日期时间字符串
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateTimeString = dateFormat.format(new Date(System.currentTimeMillis()));
            pstmt.setTimestamp(2, java.sql.Timestamp.valueOf(dateTimeString));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/javaexp3", "root", "jk200497");
    }
}