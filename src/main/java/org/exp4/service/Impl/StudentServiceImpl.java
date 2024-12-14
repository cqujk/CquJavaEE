package org.exp4.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.javafaker.Faker;
import org.exp4.model.response.StudentWithRank;
import org.exp4.repository.dao.StudentMapper;
import org.exp4.model.entity.Student;
import org.exp4.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Override
    public StudentWithRank getStudent(Integer studentId) {
        return baseMapper.getStudentRank(studentId);
    }

    @Override
    public List<StudentWithRank> all() {
        return baseMapper.getAll();
    }

    @Override
    public void clearAllGPAs() {
        baseMapper.clearAllGPAs();
    }
//    @Override
//    public boolean saveBatch(List<Student> students) {
//        return saveBatch(students);
//    }


}

