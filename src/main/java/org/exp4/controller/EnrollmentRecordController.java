package org.exp4.controller;

import org.exp4.model.entity.EnrollmentRecord;
import org.exp4.model.entity.TeachingRecord;
import org.exp4.model.response.StuCourseInfoResponse;
import org.exp4.service.EnrollmentRecordService;
import org.exp4.utils.generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/enrollment-record")
public class EnrollmentRecordController {
    @Autowired
    private EnrollmentRecordService enrollmentRecordService;
    @Autowired
    private generator generator;
    @PostMapping("/generate")
    public boolean generateEnrollmentRecords() {
        return enrollmentRecordService.saveBatch(generator.generateEnrollmentRecords());
    }
    @DeleteMapping("/clear-all")
    public boolean clearAll() {
        return enrollmentRecordService.clearAll();
    }
    @PostMapping("/add")
    public boolean addEnrollmentRecord(@RequestBody EnrollmentRecord enrollmentRecord) {
        return enrollmentRecordService.save(enrollmentRecord);
    }

    @GetMapping("/list")
    public List<EnrollmentRecord> listEnrollmentRecords() {
        return enrollmentRecordService.list();
    }

    @GetMapping("/view/{id}")
    public EnrollmentRecord viewEnrollmentRecord(@PathVariable Integer id) {
        return enrollmentRecordService.getById(id);
    }

    @PostMapping("/update")
    public boolean updateEnrollmentRecord(@RequestBody EnrollmentRecord enrollmentRecord) {
        return enrollmentRecordService.updateById(enrollmentRecord);
    }
    @GetMapping("/selectedCourse/{studentId}")
    public ResponseEntity<Map<String, List<StuCourseInfoResponse>>> getCoursesByStudentIdFromView(@PathVariable Integer studentId) {
        List<StuCourseInfoResponse> courses = enrollmentRecordService.getCoursesByStudentIdFromView(studentId);
        Map<String, List<StuCourseInfoResponse>> response = new HashMap<>();
        response.put("courses", courses);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/delete/{id}")
    public boolean deleteEnrollmentRecord(@PathVariable Integer id) {
        return enrollmentRecordService.removeById(id);
    }
}

