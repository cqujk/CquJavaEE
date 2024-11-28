package org.web1.service;



import org.web1.dao.UserSessionRedis;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserService {
    private final UserSessionRedis userSessionRed=new UserSessionRedis();
    public void insertLoginRecord(String userId) {
        System.out.println("Inserting login record for user: " + userId);
        String sql = "INSERT INTO user_login_web1 (user_id, login_time) VALUES (?, ?)";
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
