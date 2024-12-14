CREATE VIEW class_info_view AS
SELECT
    tr.*,
    t.teacher_name,
    c.course_name,c.credits,
    COUNT(er.student_id) AS student_count
FROM
    teaching_record tr
        JOIN
    course c ON tr.course_id = c.course_id
        JOIN
    teacher t ON tr.teacher_id = t.teacher_id
        LEFT JOIN
    enrollment_record er ON tr.teaching_id = er.teaching_id
GROUP BY
    tr.teaching_id, tr.course_id, tr.teacher_id, tr.semester, tr.year, tr.grade_composition, t.teacher_name, c.course_name;
