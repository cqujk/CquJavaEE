package org.exp4auth.model.response;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String password;

    public UserResponse(Long id, String password) {
        this.id = id;
        this.password = password;
    }

    // Getters and Setters
}
