package org.web2.servlet;

import org.web2.entity.UserLogin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/index")
public class HomeServlet extends HttpServlet{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<UserLogin> loginHistory = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user_login_web2")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                UserLogin login = new UserLogin();
                login.setUserId(rs.getString("user_id"));
                login.setLoginTime(rs.getTimestamp("login_time").toLocalDateTime());
                loginHistory.add(login);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("loginHistory", loginHistory);
        request.getRequestDispatcher("index.jsp").forward(request,response);
    }
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/javaexp3", "root", "jk200497");
    }
}
