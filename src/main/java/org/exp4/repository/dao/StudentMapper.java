package org.exp4.repository.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.exp4.model.entity.Student;
import org.exp4.model.response.StudentWithRank;

import java.util.List;

public interface StudentMapper extends BaseMapper<Student> {
    @Select("WITH RankedStudents AS (" +
            "    SELECT student_id, student_name, gender, major, is_active, gpa, " +
            "           RANK() OVER (ORDER BY gpa DESC) as totalRank " +
            "    FROM student" +
            ") " +
            "SELECT student_id, student_name, gender, major, is_active, gpa, totalRank " +
            "FROM RankedStudents " +
            "WHERE student_id = #{studentId}")
    StudentWithRank getStudentRank(@Param("studentId")Integer studentId);

    @Select("SELECT student_id,student_name,gender,major,is_active,gpa,RANK() OVER (ORDER BY gpa DESC) as totalRank FROM student")
    List<StudentWithRank>getAll();

    @Update("UPDATE student SET gpa = 0")
    void clearAllGPAs();
}

