package org.exp4auth.service;

import org.exp4auth.error.UserAlreadyExistsException;
import org.exp4auth.model.*;
import org.exp4auth.model.request.AuthenticationRequest;
import org.exp4auth.model.request.RegisterRequest;
import org.exp4auth.model.response.AuthenticationResponse;
import org.exp4auth.model.response.UserResponse;
import org.exp4auth.repository.UserRepository;
import org.exp4auth.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try{
            Authentication authentication = authenticationManager.authenticate(
                    // new UsernamePasswordAuthenticationToken(request.getUsername(), passwordEncoder.encode(request.getPassword()))
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            //System.out.println("AuthService has authenticated user");
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtTokenProvider.generateToken(authentication);
            return new AuthenticationResponse(token);
        } catch (Exception e) {
            System.out.println("Authentication failed for user: "+ request.getUsername()+ e);
            return null;
            //  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during authentication");
        }
    }

    public UserResponse register(RegisterRequest request) {
        // 检查用户名是否已经存在
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            System.out.println("User already exists: " + request.getUsername());
            throw new UserAlreadyExistsException("已存在相同用户名的用户");
           // throw new UserAlreadyExistsException("User with same username has existed");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        //user.setPassword(request.getPassword());
        //System.out.println("AuthService has set password : "+user.getPassword());
        user.setUserType(UserType.fromString(request.getUserType()));
        System.out.println("AuthService has set user : "+user);
        userRepository.save(user);
        //System.out.println("AuthService has saved user");
        return new UserResponse(user.getUserId(), user.getUsername(), user.getPassword());
    }

    public void logout() {
        SecurityContextHolder.clearContext();
    }
}
