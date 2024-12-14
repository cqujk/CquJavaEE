package org.exp4.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("grade_detail")
public class GradeDetail {
    private Integer enrollmentId;
    private String gradeDetail; // JSON 字段

    // Getters and Setters
}

