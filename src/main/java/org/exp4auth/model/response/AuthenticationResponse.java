package org.exp4auth.model.response;

import lombok.Data;

@Data
public class AuthenticationResponse {
    private String token;
    private String userType;
    private Long userId;
    public AuthenticationResponse(String token, String userType,Long userId) {
        this.userType = userType;
        this.token = token;
        this.userId = userId;
    }

    // Getters and Setters
}
