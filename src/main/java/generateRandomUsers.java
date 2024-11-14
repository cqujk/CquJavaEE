import service.UserService;
import utils.RC;

import java.util.UUID;

public class generateRandomUsers {
    private static final String PASSWORD = "20220669";
    private static String generateRandomUsername() {
        return "user_" + UUID.randomUUID().toString().substring(0, 8);
    }
    // 主方法，生成并插入多个用户
    public static void main(String[] args) {
        UserService userService = new UserService();
        int numberOfUsers = 10; // 可以根据需要调整
        for (int i = 0; i < numberOfUsers; i++) {
            String username = generateRandomUsername();
            String password = PASSWORD;
            try {
                RC rc =userService.register(username, password);
                if(rc==RC.SUCCESS){
                    System.out.println("Inserted user: " + username);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(numberOfUsers + " users have been inserted successfully.");
    }
}
