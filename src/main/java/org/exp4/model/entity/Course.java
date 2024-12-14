package org.exp4.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("course")
public class Course {
    @TableId(type= IdType.AUTO)
    private Integer courseId;
    private String courseName;
    private Integer credits;
    private Boolean isActive;

    // Getters and Setters
}
