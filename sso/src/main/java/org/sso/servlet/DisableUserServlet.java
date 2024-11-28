package org.sso.servlet;

import org.sso.dao.DatabaseUtils;
import org.sso.utils.CookieUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/disableUser")
public class DisableUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        // 从数据库中禁用用户
        disableUserInDatabase(userId);
        // 注销用户
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        CookieUtil.clearCookies(response);
        // 重定向到登录页面
        response.sendRedirect("index.jsp");
    }
    private void disableUserInDatabase(String userId) {
        //users表的status字段，为枚举量，设置为DISABLED
        String sql = "UPDATE users SET status = 'DISABLED' WHERE username= ?";
        DatabaseUtils.executeUpdate(sql, userId);
    }
}
