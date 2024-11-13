package entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class LoginHistory {
    private int id;
    private User user;
    private Timestamp loginTime;
}
