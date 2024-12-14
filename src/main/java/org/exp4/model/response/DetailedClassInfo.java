package org.exp4.model.response;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
@Data
@TableName("detailed_class_info_view")
public class DetailedClassInfo {
    private int teachingId;
    private int studentId;
    private BigDecimal totalGrade;
    private int enrollmentId;
    private String gradeDetail;//JSON字段
    private String studentName;
    private char gender;
    private String major;
    private BigDecimal gpa;
}
