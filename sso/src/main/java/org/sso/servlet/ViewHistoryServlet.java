package org.sso.servlet;
import org.sso.entity.UserLogin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;

@WebServlet("/viewHistory")
public class ViewHistoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<UserLogin> loginHistory = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user_login")) {
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
        request.getRequestDispatcher("history.jsp").forward(request, response);
    }
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/javaexp3", "root", "jk200497");
    }
}