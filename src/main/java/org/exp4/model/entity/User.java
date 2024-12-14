package org.exp4.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class User {
    @TableId
    private Integer userId;
    private String password;
    private String userType;
    private Boolean isActive;
}
