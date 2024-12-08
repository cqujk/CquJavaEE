package org.exp4auth.model.request;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String username;
    private String password;

    // Getters and Setters
}
