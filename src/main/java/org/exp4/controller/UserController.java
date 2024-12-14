package org.exp4.controller;

import org.exp4.model.entity.Course;
import org.exp4.model.entity.Teacher;
import org.exp4.model.entity.User;
import org.exp4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping("/list")
    public ResponseEntity<Map<String, List<User>>> listUsers() {
        //System.out.println("listCourses has touch");
        List<User> userList = userService.list();
        Map<String, List<User>> response = new HashMap<>();
        response.put("users", userList);
        return ResponseEntity.ok(response);
    }
}
