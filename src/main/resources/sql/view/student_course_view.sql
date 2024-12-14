CREATE VIEW student_courses_view AS
SELECT
    er.student_id,
    c.course_name,
    c.credits,
    er.total_grade
FROM
    enrollment_record er
JOIN
    teaching_record tr ON er.teaching_id = tr.teaching_id
JOIN
    course c ON tr.course_id = c.course_id;
