package org.exp4.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.exp4.repository.dao.CourseMapper;
import org.exp4.model.entity.Course;
import org.exp4.service.CourseService;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
}

