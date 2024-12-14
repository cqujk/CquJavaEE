package org.exp4.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.exp4.model.entity.EnrollmentRecord;
import org.exp4.model.response.StuCourseInfoResponse;

import java.util.List;

public interface EnrollmentRecordService extends IService<EnrollmentRecord> {
    List<StuCourseInfoResponse> getCoursesByStudentIdFromView(Integer studentId);

    boolean clearAll();

    void clearAllGrades();
}

