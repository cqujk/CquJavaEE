package org.exp4auth.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;

@Entity
@Data
//@TableName("user")
public class User implements UserDetails {
    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @TableId(type = IdType.AUTO)
    private Long userId;
    //@TableField(value = "user_name")
    private String password;
    @Enumerated(EnumType.STRING)
    private UserType userType; // 使用枚举类型

    private Boolean isActive;
    // Getters and Setters
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 根据 userType 返回相应的权限
        return  Arrays.asList(new SimpleGrantedAuthority("ROLE_" + userType.name().toUpperCase()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        System.out.println("the getUsername function has been touch");
        return String.valueOf(userId);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}