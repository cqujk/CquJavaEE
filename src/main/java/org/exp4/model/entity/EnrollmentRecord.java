package org.exp4.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("enrollment_record")
public class EnrollmentRecord {
    @TableId(type= IdType.AUTO)
    private Integer enrollmentId;
    private Integer studentId;
    private Integer teachingId;
    private BigDecimal totalGrade;
    // Getters and Setters
}

