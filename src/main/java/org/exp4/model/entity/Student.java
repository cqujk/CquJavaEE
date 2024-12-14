package org.exp4.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("student")
public class Student {
    @TableId(type= IdType.AUTO)
    private Integer studentId;
    private String studentName;
    private Character gender;
    private String major;
    private Boolean isActive;
    private BigDecimal gpa;

    public Student(String studentId, String studentName, Character gender, String major, Boolean isActive) {
        this.studentId = Integer.parseInt(studentId);
        this.studentName = studentName;
        this.gender = gender;
        this.major = major;
        this.isActive = isActive;
    }

    // Getters and Setters
}
