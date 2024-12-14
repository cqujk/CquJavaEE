package org.exp4.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.exp4.model.response.ClassInfo;
import org.exp4.model.response.DetailedClassInfo;
import org.exp4.repository.dao.TeachingRecordMapper;
import org.exp4.model.entity.TeachingRecord;
import org.exp4.service.TeachingRecordService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeachingRecordServiceImpl extends ServiceImpl<TeachingRecordMapper, TeachingRecord> implements TeachingRecordService {
    @Override
    public List<ClassInfo> getClassInfoByTeachingId(int teacherId) {
        return baseMapper.getClassInfoByTeachingId(teacherId);
    }

    @Override
    public List<ClassInfo> getAllClassInfo() {
        return baseMapper.getAllClassInfo();
    }

    @Override
    public List<DetailedClassInfo> getDetailedClassInfoByTeachingId(int teachingId) {
        return baseMapper.getDetailedClassInfoByTeachingId(teachingId);
    }
}

