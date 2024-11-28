package org.web2.servlet;




import org.web2.dao.UserSessionRedis;
import org.web2.utils.CookieUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private UserSessionRedis sessionManager = new UserSessionRedis();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取当前用户的ID
        String userId = (String) request.getSession().getAttribute("userId");
        if (userId != null) {
            // 从Redis中移除用户信息
            sessionManager.logout(userId);
            // 使当前会话失效
            request.getSession().invalidate();
        }
        CookieUtil.clearCookies(response);
        // 重定向到登录页面或其他页面
        response.sendRedirect("http://127.0.0.1:9003/index");
    }
}
