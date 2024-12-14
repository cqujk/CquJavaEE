package org.exp4.controller;

import org.exp4.model.entity.TeachingRecord;
import org.exp4.model.response.ClassInfo;
import org.exp4.model.response.DetailedClassInfo;
import org.exp4.service.TeachingRecordService;
import org.exp4.utils.generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/teaching-record")
public class TeachingRecordController {
    @Autowired
    private TeachingRecordService teachingRecordService;
    @Autowired
    private generator generator;
    @GetMapping("/teacher-class-info-detail")
    public ResponseEntity<Map<String,List<DetailedClassInfo>>> getDetailedClassInfo(@RequestParam int teachingId){
        List<DetailedClassInfo> detailedClassInfoList = teachingRecordService.getDetailedClassInfoByTeachingId(teachingId);
        Map<String, List<DetailedClassInfo>> response = new HashMap<>();
        response.put("detailedClassInfo", detailedClassInfoList);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/teacher-class-info")
    public ResponseEntity<Map<String,List<ClassInfo>>> getClassInfo(@RequestParam int teacherId) {
        List<ClassInfo> classInfoList = teachingRecordService.getClassInfoByTeachingId(teacherId);
        Map<String, List<ClassInfo>> response = new HashMap<>();
        response.put("classInfo", classInfoList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all-class-info")
    public ResponseEntity<Map<String,List<ClassInfo>>>getAllClass(){
        List<ClassInfo> classInfoList =teachingRecordService.getAllClassInfo();
        Map<String, List<ClassInfo>> response = new HashMap<>();
        response.put("classInfo", classInfoList);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public boolean addTeachingRecord(@RequestBody TeachingRecord teachingRecord) {
        return teachingRecordService.save(teachingRecord);
    }
    @PostMapping("/generate")
    public boolean generateTeacher() {
        List<TeachingRecord> records = generator.generateTeachingRecords();
        return teachingRecordService.saveBatch(records);
    }

    @GetMapping("/list")
    public List<TeachingRecord> listTeachingRecords() {
        return teachingRecordService.list();
    }

    @GetMapping("/view/{id}")
    public TeachingRecord viewTeachingRecord(@PathVariable Integer id) {
        return teachingRecordService.getById(id);
    }

    @PostMapping("/update")
    public boolean updateTeachingRecord(@RequestBody TeachingRecord teachingRecord) {
        return teachingRecordService.updateById(teachingRecord);
    }

    @GetMapping("/delete/{id}")
    public boolean deleteTeachingRecord(@PathVariable Integer id) {
        return teachingRecordService.removeById(id);
    }
}
