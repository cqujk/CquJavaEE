package org.exp4.controller;

import org.exp4.model.entity.Student;
import org.exp4.model.response.StudentWithRank;
import org.exp4.service.StudentService;
import org.exp4.utils.generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private generator generator;

    @PostMapping("/add")
    public boolean addStudent(@RequestBody Student student) {
        return studentService.save(student);
    }

//    @GetMapping("/list")
//    public List<Student> listStudents() {
//        return studentService.list();
//    }

    @GetMapping("/view/{id}")
    public Student viewStudent(@PathVariable Integer id) {
        System.out.println("student controller has get view student request,the id is: "+ id);
        return studentService.getById(id);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Map<String,StudentWithRank>> getStudent(@PathVariable Integer id) {
        StudentWithRank stu=studentService.getStudent(id);
        Map<String,StudentWithRank>res=new HashMap<>();
        res.put("student",stu);
        return ResponseEntity.ok(res);
    }
    @GetMapping("/all-stu")
    public ResponseEntity<Map<String,List<StudentWithRank>>> getAllStudents() {
        List<StudentWithRank> students = studentService.all();
        Map<String, List<StudentWithRank>> response = new HashMap<>();
        response.put("students", students);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/update")
    public boolean updateStudent(@RequestBody Student student) {
        return studentService.updateById(student);
    }
    @PostMapping("/generate")
    public boolean generateStudents(@RequestParam int year, @RequestParam int count) {
        List<Student> students = generator.generateStudents(year, count);
        System.out.println("Generated students: " + students);
        return studentService.saveBatch(students);
    }
    @GetMapping("/delete/{id}")
    public boolean deleteStudent(@PathVariable Integer id) {
        return studentService.removeById(id);
    }
}
