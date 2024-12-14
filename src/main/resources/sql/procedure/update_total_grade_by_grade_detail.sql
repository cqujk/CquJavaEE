DELIMITER //

CREATE PROCEDURE update_total_grade(IN p_enrollment_id INT)
BEGIN
    DECLARE v_total_grade DECIMAL(5, 2) DEFAULT 0.00;
    DECLARE v_grade_composition JSON;
    DECLARE v_grade_segment VARCHAR(50);
    DECLARE v_grade_value DECIMAL(5, 2);
    DECLARE v_weight DECIMAL(5, 2);
    DECLARE v_grade_detail JSON;
    DECLARE v_index INT DEFAULT 0;
    DECLARE v_count INT DEFAULT 0;

    -- 获取教学记录的评分构成
SELECT grade_composition INTO v_grade_composition
FROM teaching_record tr
         JOIN enrollment_record er ON tr.teaching_id = er.teaching_id
WHERE er.enrollment_id = p_enrollment_id;

-- 获取 grade_detail 表中的 JSON 数据
SELECT grade_detail INTO v_grade_detail
FROM grade_detail gd
WHERE gd.enrollment_id = p_enrollment_id;

-- 获取 JSON 数组的长度
SET v_count = JSON_LENGTH(v_grade_detail);

    -- 遍历 JSON 数组
    WHILE v_index < v_count DO
        -- 获取当前成绩段和成绩值
        SET v_grade_segment = JSON_UNQUOTE(JSON_EXTRACT(v_grade_detail, CONCAT('$[', v_index, '].grade_segment')));
        SET v_grade_value = JSON_UNQUOTE(JSON_EXTRACT(v_grade_detail, CONCAT('$[', v_index, '].grade_value')));

        -- 获取当前成绩段的权重
        SET v_weight = JSON_UNQUOTE(JSON_EXTRACT(v_grade_composition, CONCAT('$.', v_grade_segment)));

        -- 计算加权成绩
        SET v_total_grade = v_total_grade + (v_grade_value * v_weight / 100);

        -- 增加索引
        SET v_index = v_index + 1;
END WHILE;

    -- 更新 enrollment_record 表中的 total_grade
UPDATE enrollment_record
SET total_grade = v_total_grade
WHERE enrollment_id = p_enrollment_id;
END //

DELIMITER ;
