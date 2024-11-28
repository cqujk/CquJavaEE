package org.sso.service;

import org.sso.dao.UserSessionRedis;
import org.sso.entity.User;
import org.sso.dao.DatabaseUtils;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {
    private final UserSessionRedis userSessionRed=new UserSessionRedis();

    public boolean validateUser(String username, String password) throws SQLException {
        // 这里实现实际的用户验证逻辑
        // 例如，查询数据库验证用户名和密码
        return !authenticateUser(username, password).equals("DISABLED");
    }
    private String authenticateUser(String username, String password) throws SQLException {
        // 实现用户验证逻辑
        // 示例：
         String sql = "SELECT status FROM users WHERE username = ? AND password = ?";
//         ResultSet rs = DatabaseUtils.executeQuery(sql, username, password);
//        // if (rs.next()) {
//        //     return new User(rs.getString("user_id"), rs.getString("username"), rs.getString("password"), rs.getBoolean("is_disabled"));
//        // }
//        if (rs != null && rs.next()) {
//            return rs.getString("status");
//        }
//        return "IDLE";
        try (ResultSet rs = DatabaseUtils.executeQuery(sql, username, password)) {
            if (rs != null && rs.next()) {
                return rs.getString("status");
            }
        }
        return "IDLE";
    }
}
