package org.exp4.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("teacher")
public class Teacher {
    @TableId(type= IdType.AUTO)
    private Integer teacherId;
    private String teacherName;
    private Boolean isActive;

    // Getters and Setters
}
