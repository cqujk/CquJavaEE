DELIMITER //

CREATE TRIGGER update_gpa_after_grade_change
AFTER UPDATE ON enrollment_record
FOR EACH ROW
BEGIN
    IF OLD.total_grade <> NEW.total_grade THEN
        CALL calculate_gpa(NEW.student_id);
    END IF;
END //

DELIMITER ;
