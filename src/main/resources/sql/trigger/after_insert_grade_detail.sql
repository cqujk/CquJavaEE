DELIMITER //

CREATE TRIGGER after_insert_grade_detail
AFTER INSERT ON grade_detail
FOR EACH ROW
BEGIN
    CALL update_total_grade(NEW.enrollment_id);
END //

DELIMITER ;
