package service;

import Dao.UserDao;
import entity.User;
import entity.UserStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.RC;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class UserService {
    private static final Logger logger = LogManager.getLogger(UserService.class);
    private UserDao userDao;
    public UserService() {
        Properties properties = new Properties();
        try(InputStream input = getClass().getClassLoader().getResourceAsStream("jdbc.properties")){
            properties.load(input);
            String driver = properties.getProperty("jdbc.driver");
            String url= properties.getProperty("jdbc.url");
            String username=properties.getProperty("jdbc.username");
            String password=properties.getProperty("jdbc.password");
            Class.forName(driver);
            logger.info("driver名称是"+driver);
            Connection conn = DriverManager.getConnection(url,username,password);
            userDao=new UserDao(conn);
        }catch(Exception e){
            logger.error("Error initializing UserService", e);
        }
    }
    public RC authenticate(String username, String password){
        try{
            User user=userDao.findUserByUsername(username);
            if(user==null){
                return RC.USER_NOT_FOUND;
            }
            if(!user.getPassword().equals(password)){
                return RC.WRONG_PASSWORD;
            }
            if(user.getStatus()== UserStatus.DISABLED){
                return RC.USER_DISABLED;
            }
            return RC.SUCCESS;
        }catch(Exception e){
            logger.error("Error authenticating user", e);
            return RC.AUTHENTICATION_FAILED;
        }
    }
    public RC register(String username, String password){
        try{
            User user=userDao.registerUser(username,password);
            if(user == null){
                return RC.USER_EXITS;
            }
            return RC.SUCCESS;
        }catch(Exception e){
            logger.error("Error registering user", e);
            return RC.AUTHENTICATION_FAILED;
        }
    }
}

