package org.exp4.controller;

import org.exp4.model.entity.GradeDetail;
import org.exp4.service.GradeDetailService;
import org.exp4.utils.generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grade-detail")
public class GradeDetailController {
    @Autowired
    private GradeDetailService gradeDetailService;
    @Autowired
    private generator generator;
    @PostMapping("/generate")
    public boolean generateGradeDetails() {
        List<GradeDetail> details = generator.generateGradeDetail();
        return gradeDetailService.saveBatch(details);
    }
    @DeleteMapping("/clear-all")
    public boolean clearAllGradeDetails() {
        return gradeDetailService.clearAll();
    }
    @PostMapping("/add")
    public boolean addGradeDetail(@RequestBody GradeDetail gradeDetail) {
        return gradeDetailService.save(gradeDetail);
    }

    @GetMapping("/list")
    public List<GradeDetail> listGradeDetails() {
        return gradeDetailService.list();
    }

    @GetMapping("/view/{id}")
    public GradeDetail viewGradeDetail(@PathVariable Integer id) {
        return gradeDetailService.getById(id);
    }

    @PostMapping("/update")
    public boolean updateGradeDetail(@RequestBody GradeDetail gradeDetail) {
        return gradeDetailService.updateById(gradeDetail);
    }

    @GetMapping("/delete/{id}")
    public boolean deleteGradeDetail(@PathVariable Integer id) {
        return gradeDetailService.removeById(id);
    }
}
