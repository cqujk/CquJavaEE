package org.exp4.controller;

import org.exp4.model.entity.Course;
import org.exp4.model.response.StuCourseInfoResponse;
import org.exp4.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping("/add")
    public boolean addCourse(@RequestBody Course course) {
        return courseService.save(course);
    }

    @GetMapping("/list")
    public ResponseEntity<Map<String, List<Course>>>  listCourses() {
        System.out.println("listCourses has touch");
        List<Course> courseList = courseService.list();
        Map<String, List<Course>> response = new HashMap<>();
        response.put("courses", courseList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/view/{id}")
    public Course viewCourse(@PathVariable Integer id) {
        return courseService.getById(id);
    }

    @PostMapping("/update")
    public boolean updateCourse(@RequestBody Course course) {
        return courseService.updateById(course);
    }

    @GetMapping("/delete/{id}")
    public boolean deleteCourse(@PathVariable Integer id) {
        return courseService.removeById(id);
    }
}

