package org.exp4auth.model.request;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private Long id;
    private String password;

    // Getters and Setters
}
