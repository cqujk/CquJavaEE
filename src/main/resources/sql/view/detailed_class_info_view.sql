CREATE VIEW detailed_class_info_view AS
SELECT
    er.teaching_id,
    er.student_id,
    er.total_grade,
    er.enrollment_id,
    gd.grade_detail,
    s.student_name,
    s.gender,
    s.major,
    s.gpa
FROM
    enrollment_record er
        LEFT JOIN
    grade_detail gd ON er.enrollment_id = gd.enrollment_id
        LEFT JOIN
    student s ON er.student_id = s.student_id AND s.is_active = 1

