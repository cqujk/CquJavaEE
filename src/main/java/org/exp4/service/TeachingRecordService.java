package org.exp4.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.exp4.model.entity.TeachingRecord;
import org.exp4.model.response.ClassInfo;
import org.exp4.model.response.DetailedClassInfo;

import java.util.List;

public interface TeachingRecordService extends IService<TeachingRecord> {
    List<ClassInfo> getClassInfoByTeachingId(int teacherId);
    List<ClassInfo> getAllClassInfo();
    List<DetailedClassInfo> getDetailedClassInfoByTeachingId(int teachingId);
}

