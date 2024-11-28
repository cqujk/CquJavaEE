package org.web2.entity;



import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserLogin {
    private String userId;
    private LocalDateTime loginTime;
}
