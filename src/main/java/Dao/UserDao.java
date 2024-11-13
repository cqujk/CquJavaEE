package Dao;

import entity.User;
import entity.UserStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private Connection connection;
    public UserDao(Connection connection) {
        this.connection = connection;
    }
    public void recoverUser(User user) throws SQLException{
        String updateUser = "update user set status = 'OFFLINE' where id = ?";
        PreparedStatement updateStatement = connection.prepareStatement(updateUser);
        updateStatement.setInt(1,user.getId());
        updateStatement.executeUpdate();
    }
    public void banUser(User user) throws SQLException{
        String updateUser = "update user set status = 'DISABLED' where id = ?";
        PreparedStatement updateStatement = connection.prepareStatement(updateUser);
        updateStatement.setInt(1,user.getId());
        updateStatement.executeUpdate();
    }
    public void logout(User user) throws SQLException {
        String updateUser = "update user set status = 'OFFLINE' where id = ?";
        PreparedStatement updateStatement = connection.prepareStatement(updateUser);
        updateStatement.setInt(1,user.getId());
        updateStatement.executeUpdate();
    }
    public User registerUser(String username, String password) throws SQLException {
        String CreateSql = "INSERT INTO users (username,password) VALUES (?,?)";
        PreparedStatement statement=connection.prepareStatement(CreateSql,PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setString(1,username);
        statement.setString(2,password);
        statement.executeUpdate();
        ResultSet generatedResultSet = statement.getGeneratedKeys();
        if(generatedResultSet.next()){
            User user = new User();
            user.setId(generatedResultSet.getInt(1));
            user.setUsername(username);
            user.setPassword(password);
            return user;
        }
        return null;
    }
    public User findUserByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,username);
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next()){
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            user.setStatus(UserStatus.valueOf(resultSet.getString("status")));
            return user;
        }
        return null;
    }
    public List<User> getAllUsers() throws SQLException{
        String sql = "SELECT * FROM users";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        List<User> users= new ArrayList<>();
        while(resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            user.setStatus(UserStatus.valueOf(resultSet.getString("status")));
            users.add(user);
        }
        return users;
    }
}
