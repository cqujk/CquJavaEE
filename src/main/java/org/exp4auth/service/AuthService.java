package org.exp4auth.service;

import org.exp4auth.error.UserAlreadyExistsException;
import org.exp4auth.model.*;
import org.exp4auth.model.request.AuthenticationRequest;
import org.exp4auth.model.request.RegisterRequest;
import org.exp4auth.model.response.AuthenticationResponse;
import org.exp4auth.model.response.UserResponse;
import org.exp4auth.repository.UserRepository;
import org.exp4auth.security.JwtTokenProvider;
import org.exp4auth.utils.UserTypeUtils;
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
                    new UsernamePasswordAuthenticationToken(request.getId(), request.getPassword())
            );
            //System.out.println("AuthService has authenticated user");
            //登陆时要求用户的is_active为1,不然则为已经冻结的状态
            User user = userRepository.findByUserId(request.getId()).orElse(null);
            if (user == null || !user.isEnabled()) {
                System.out.println("User is not active or does not exist");
                return null;
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtTokenProvider.generateToken(authentication);
            UserType userType = UserTypeUtils.getUserTypeFromUserId(String.valueOf(request.getId()));
            return new AuthenticationResponse(token, userType.toUpperCase(), request.getId());
        } catch (Exception e) {
            System.out.println("Authentication failed for user: "+ request.getId()+ e);
            return null;
            //  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during authentication");
        }
    }

    public UserResponse register(RegisterRequest request) {
        // 检查用户名是否已经存在
        if (userRepository.findByUserId(request.getId()).isPresent()) {
            System.out.println("User already exists: " + request.getId());
            throw new UserAlreadyExistsException("已存在相同用户名的用户");
           // throw new UserAlreadyExistsException("User with same username has existed");
        }
        User user = new User();
        user.setUserId(request.getId());
        user.setIsActive(true);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUserType(UserTypeUtils.getUserTypeFromUserId(String.valueOf(request.getId())));
        //user.setPassword(request.getPassword());
        //System.out.println("AuthService has set password : "+user.getPassword());
        //user.setUserType(UserType.fromString(request.getUserType()));

        System.out.println("AuthService has set user : "+user);
        userRepository.save(user);
        //System.out.println("AuthService has saved user");
        return new UserResponse(user.getUserId(), user.getPassword());
    }

    public void logout(Long userId) {
        SecurityContextHolder.clearContext();
        //要求依据userId找到用户，然后将该用户的is_active设置为0
        User user = userRepository.findByUserId(userId).orElse(null);
        System.out.println("AuthService has logged out user: "+user);
        if (user != null) {
            user.setIsActive(false);
            userRepository.save(user);
        }
    }

    public void recover(Long userId) {
        User user = userRepository.findByUserId(userId).orElse(null);
        if (user != null) {
            user.setIsActive(true);
            userRepository.save(user);
        }
    }
}
