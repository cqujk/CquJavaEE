package org.exp4.repository.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.exp4.model.entity.EnrollmentRecord;
import org.exp4.model.response.StuCourseInfoResponse;

import java.util.List;

public interface EnrollmentRecordMapper extends BaseMapper<EnrollmentRecord> {
    @Select("SELECT course_name, credits, total_grade " +
            "FROM student_courses_view " +
            "WHERE student_id = #{studentId}")
    List<StuCourseInfoResponse> getCoursesByStudentIdFromView(@Param("studentId") Integer studentId);

    @Update("UPDATE enrollment_record SET  total_grade= NULL")
    void clearAllGrades();

}

