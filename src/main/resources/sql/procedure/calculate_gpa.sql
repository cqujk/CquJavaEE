DELIMITER //

CREATE  PROCEDURE calculate_gpa(IN p_student_id INT)
BEGIN
    DECLARE v_total_grade_credits_sum DECIMAL(6, 2) DEFAULT 0.00;
    DECLARE v_total_credits INT DEFAULT 0;
    DECLARE v_gpa DECIMAL(5, 2);

    -- 计算总成绩乘以学分的和以及总学分
    SELECT SUM(er.total_grade * c.credits), SUM(c.credits) INTO v_total_grade_credits_sum, v_total_credits
    FROM enrollment_record er
    JOIN teaching_record tr ON er.teaching_id = tr.teaching_id
    JOIN course c ON tr.course_id = c.course_id
    WHERE er.student_id = p_student_id;

    -- 计算 GPA
    IF v_total_credits > 0 THEN
        SET v_gpa = v_total_grade_credits_sum / v_total_credits;
    ELSE
        SET v_gpa = 0.00;
    END IF;

    -- 更新学生的 GPA
    UPDATE student
    SET gpa = v_gpa
    WHERE student_id = p_student_id;

    -- 插入日志信息
    INSERT INTO procedure_log (log_message) VALUES (CONCAT('Updated GPA for student_id ', p_student_id, ': ', v_gpa));
END //

DELIMITER ;
