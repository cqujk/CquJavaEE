package org.exp4auth.model.request;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String userType;
    // Getters and Setters
}
