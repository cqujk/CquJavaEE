package org.exp4.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.exp4.repository.dao.GradeDetailMapper;
import org.exp4.model.entity.GradeDetail;
import org.exp4.service.EnrollmentRecordService;
import org.exp4.service.GradeDetailService;
import org.exp4.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GradeDetailServiceImpl extends ServiceImpl<GradeDetailMapper, GradeDetail> implements GradeDetailService {
    @Autowired
    private StudentService studentService;
    @Autowired
    private EnrollmentRecordService enrollmentRecordService;
    @Override
    public boolean clearAll() {
        // 清空所有学生的GPA
        studentService.clearAllGPAs();
        enrollmentRecordService.clearAllGrades();
        return this.remove(null);
    }
}

