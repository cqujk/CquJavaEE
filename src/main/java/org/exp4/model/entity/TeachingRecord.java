package org.exp4.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("teaching_record")
public class TeachingRecord {
    @TableId(type= IdType.AUTO)
    private Integer teachingId;
    private Integer courseId;
    private Integer teacherId;
    private String semester;
    private Integer year;
    private String gradeComposition; // JSON 字段

    // Getters and Setters
}
