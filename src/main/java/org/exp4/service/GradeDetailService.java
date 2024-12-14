package org.exp4.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.exp4.model.entity.GradeDetail;

public interface GradeDetailService extends IService<GradeDetail> {
    boolean clearAll();
}

