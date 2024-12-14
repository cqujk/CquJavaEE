package org.exp4.model.response;

import lombok.Data;

@Data
public class StuCourseInfoResponse {
    private String courseName;
    private int credits;
    private double totalGrade;
}
