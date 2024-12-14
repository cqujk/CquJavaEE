DELIMITER //

CREATE TRIGGER after_update_grade_detail
AFTER UPDATE ON grade_detail
FOR EACH ROW
BEGIN
    CALL update_total_grade(NEW.enrollment_id);
END //

DELIMITER ;
