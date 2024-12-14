package org.exp4.model.response;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class StudentWithRank {
    private Integer studentId;
    private String studentName;
    private Character gender;
    private String major;
    private Boolean isActive;
    private BigDecimal gpa;
    private int totalRank;
}
