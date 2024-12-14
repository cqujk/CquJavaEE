package org.exp4.controller;

import org.exp4.model.entity.Student;
import org.exp4.model.entity.Teacher;
import org.exp4.service.TeacherService;
import org.exp4.utils.generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private generator generator;
    @PostMapping("/add")
    public boolean addTeacher(@RequestBody Teacher teacher) {
        return teacherService.save(teacher);
    }
    @PostMapping("/generate")
    public boolean generateStudents(@RequestParam int year, @RequestParam int count) {
        List<Teacher> teachers = generator.generateTeachers(year, count);
        System.out.println("Generated teachers: " + teachers);
        return teacherService.saveBatch(teachers);
    }
    @GetMapping("/list")
    public List<Teacher> listTeachers() {
        return teacherService.list();
    }

    @GetMapping("/view/{id}")
    public Teacher viewTeacher(@PathVariable Integer id) {
        return teacherService.getById(id);
    }

    @PostMapping("/update")
    public boolean updateTeacher(@RequestBody Teacher teacher) {
        return teacherService.updateById(teacher);
    }

    @GetMapping("/delete/{id}")
    public boolean deleteTeacher(@PathVariable Integer id) {
        return teacherService.removeById(id);
    }
}
