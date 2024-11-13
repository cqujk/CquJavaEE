package Dao;

import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LoginHistoryDao {
    private Connection connection;
    public void login(User user) throws SQLException {
        String CLHsql = "insert into login_history(user_id,login_time) values(?,?)";
        PreparedStatement statement = connection.prepareStatement(CLHsql);
        statement.setInt(1,user.getId());
        statement.setTimestamp(2,new java.sql.Timestamp(System.currentTimeMillis()));
        statement.executeUpdate();
        String updateUser = "update user set status = 'ONLINE' where id = ?";
        PreparedStatement updateStatement = connection.prepareStatement(updateUser);
        updateStatement.setInt(1,user.getId());
        updateStatement.executeUpdate();
    }

}
