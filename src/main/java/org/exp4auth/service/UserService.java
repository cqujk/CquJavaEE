package org.exp4auth.service;

import org.exp4auth.model.User;
import org.exp4auth.model.response.UserResponse;
import org.exp4auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserService  implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            System.out.println("User found: " + user);
            // 将角色列表转换为GrantedAuthority列表
            Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
            // 创建Spring Security的User对象
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(), // 确保密码已加密
                    true, // enabled
                    true, // accountNonExpired
                    true, // credentialsNonExpired
                    true, // accountNonLocked
                    authorities);
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

    }
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponse(user.getUserId(), user.getUsername(), user.getPassword()))
                .collect(Collectors.toList());
    }
}

