package org.sso.servlet;

import org.sso.dao.DatabaseUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/recoverUser")
public class RecoverUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IOException {
        String userId = request.getParameter("userId");

        // 从数据库中恢复用户
        recoverUserInDatabase(userId);
        response.sendRedirect("index.jsp");
    }

    private void recoverUserInDatabase(String userId) {
        String sql = "UPDATE users SET status = 'OFFLINE' WHERE username = ?";
        DatabaseUtils.executeUpdate(sql, userId);
    }
}
