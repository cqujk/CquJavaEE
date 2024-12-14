package org.exp4.model.response;

import lombok.Data;
import org.exp4.model.entity.TeachingRecord;
@Data
public class ClassInfo {
//    private TeachingRecord teachingRecord;
    private Integer teachingId;
    private Integer courseId;
    private Integer teacherId;
    private String semester;
    private Integer year;
    private Integer credits;
    private String gradeComposition; // JSON 字段
    private String teacherName;
    private String courseName;
    private int studentCount;
    // Getters and Setters
}