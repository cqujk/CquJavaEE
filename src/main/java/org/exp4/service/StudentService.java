package org.exp4.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.exp4.model.entity.Student;
import org.exp4.model.response.StudentWithRank;

import java.util.List;

public interface StudentService extends IService<Student> {
    // boolean saveBatch(List<Student> students);
    StudentWithRank getStudent(Integer studentId);
    List<StudentWithRank> all();
    void clearAllGPAs();
}
