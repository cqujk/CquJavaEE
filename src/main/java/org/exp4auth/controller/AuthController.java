package org.exp4auth.controller;
import org.exp4auth.model.request.AuthenticationRequest;
import org.exp4auth.model.request.LogoutRequest;
import org.exp4auth.model.response.AuthenticationResponse;
import org.exp4auth.model.request.RegisterRequest;
import org.exp4auth.model.response.UserResponse;
import org.exp4auth.security.JwtTokenProvider;
import org.exp4auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        System.out.println("Auth controller has get request: "+ request);
        AuthenticationResponse response = authService.authenticate(request);
        if(response == null){
            System.out.println("login failed");
            return ResponseEntity.status(401).build();
        }
        //System.out.println("login success, this is response: "+ response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest request) {
        System.out.println("Auth controller has get request: "+ request);
        UserResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequest request) {
        System.out.println("Auth controller has get logout request: "+ request);
        authService.logout(request.getUserId());
        return ResponseEntity.ok().build();
    }
    @PostMapping("/recover")
    public ResponseEntity recover(@RequestBody LogoutRequest request) {
        System.out.println("Auth controller has get recover request: "+ request);
        authService.recover(request.getUserId());
        return ResponseEntity.ok().build();
    }
    @GetMapping("/check")
    public ResponseEntity check(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("AuthController has get a check request: "+ request);
        String token = jwtTokenProvider.resolveToken(request);
        System.out.println("token: " + token);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.status(401).build();
        }
    }
}
