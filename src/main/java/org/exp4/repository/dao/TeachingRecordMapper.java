package org.exp4.repository.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.exp4.model.entity.TeachingRecord;
import org.exp4.model.response.ClassInfo;
import org.exp4.model.response.DetailedClassInfo;

import java.util.List;

public interface TeachingRecordMapper extends BaseMapper<TeachingRecord> {
    @Select("SELECT * FROM class_info_view WHERE teacher_id = #{teacherId}")
    List<ClassInfo> getClassInfoByTeachingId(@Param("teacherId") int teacherId);
    @Select("SELECT * FROM class_info_view")
    List<ClassInfo>getAllClassInfo();
    @Select("SELECT * FROM detailed_class_info_view WHERE teaching_id = #{teachingId}")
    List<DetailedClassInfo> getDetailedClassInfoByTeachingId(@Param("teachingId") int teachingId);
}
