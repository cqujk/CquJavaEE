CREATE TABLE course (
                        course_id INT AUTO_INCREMENT PRIMARY KEY,
                        course_name VARCHAR(255),
                        credits INT,
                        is_active BOOLEAN DEFAULT TRUE
);
CREATE TABLE teacher (
                         teacher_id INT AUTO_INCREMENT PRIMARY KEY,
                         teacher_name VARCHAR(255),
                         is_active BOOLEAN DEFAULT TRUE
);
CREATE TABLE student (
                         student_id INT AUTO_INCREMENT PRIMARY KEY,
                         student_name VARCHAR(255),
                         gender CHAR(1),
                         major VARCHAR(255),
                         is_active BOOLEAN DEFAULT TRUE
);
CREATE TABLE teaching_record (
                          teaching_id INT AUTO_INCREMENT PRIMARY KEY,
                          course_id INT,
                          teacher_id INT,
                          semester VARCHAR(255),
                          year INT,
                          GradeComposition JSON,
                          FOREIGN KEY (course_id) REFERENCES course(course_id),
                          FOREIGN KEY (teacher_id) REFERENCES teacher(teacher_id)
);

CREATE TABLE enrollment_record (
                            enrollment_id INT AUTO_INCREMENT PRIMARY KEY,
                            student_id INT,
                            teaching_id INT,
                            total_grade DECIMAL(5, 2),
                            FOREIGN KEY (student_id) REFERENCES student(student_id),
                            FOREIGN KEY (teaching_id) REFERENCES teaching_record(teaching_id)
);
CREATE TABLE grade_detail (
                             enrollment_id INT ,
                             GradeDetail JSON,
                             FOREIGN KEY (enrollment_id) REFERENCES enrollment_record(enrollment_id)
);
CREATE TABLE user(
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(255),
    password VARCHAR(255),
    user_type  ENUM('student', 'teacher', 'admin') NOT NULL
)