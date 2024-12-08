package org.exp4auth.model.response;

import lombok.Data;

@Data
public class UserResponse {
    private Integer id;
    private String username;
    private String password;

    public UserResponse(Integer id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    // Getters and Setters
}
