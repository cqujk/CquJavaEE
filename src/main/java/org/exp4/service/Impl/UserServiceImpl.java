package org.exp4.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.exp4.model.entity.Teacher;
import org.exp4.model.entity.User;
import org.exp4.repository.dao.TeacherMapper;
import org.exp4.repository.dao.UserMapper;
import org.exp4.service.TeacherService;
import org.exp4.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
