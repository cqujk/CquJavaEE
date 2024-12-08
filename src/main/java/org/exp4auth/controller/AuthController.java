package org.exp4auth.controller;
import org.exp4auth.model.request.AuthenticationRequest;
import org.exp4auth.model.response.AuthenticationResponse;
import org.exp4auth.model.request.RegisterRequest;
import org.exp4auth.model.response.UserResponse;
import org.exp4auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
         System.out.println("Auth controller has get request: "+ request);
        AuthenticationResponse response = authService.authenticate(request);
        if(response == null){
            System.out.println("login failed");
            return ResponseEntity.status(401).build();
        }
        System.out.println("login success, this is response: "+ response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest request) {
        //System.out.println("Auth controller has get request: "+ request);
        UserResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        authService.logout();
        return ResponseEntity.ok().build();
    }
}
