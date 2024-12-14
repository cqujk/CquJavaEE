package org.exp4.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.exp4.model.response.StuCourseInfoResponse;
import org.exp4.repository.dao.EnrollmentRecordMapper;
import org.exp4.model.entity.EnrollmentRecord;
import org.exp4.service.EnrollmentRecordService;
import org.exp4.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentRecordServiceImpl extends ServiceImpl<EnrollmentRecordMapper, EnrollmentRecord> implements EnrollmentRecordService {

    @Override
    public List<StuCourseInfoResponse> getCoursesByStudentIdFromView(Integer studentId) {
        return baseMapper.getCoursesByStudentIdFromView(studentId);
    }
    @Override
    public boolean clearAll() {
        return this.remove(null);
    }

    @Override
    public void clearAllGrades() {
        baseMapper.clearAllGrades();
    }
}

