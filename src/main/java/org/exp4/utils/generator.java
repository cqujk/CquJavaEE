package org.exp4.utils;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.google.gson.Gson;
import org.exp4.model.entity.*;
import org.exp4.service.Impl.TeachingRecordServiceImpl;
import org.exp4.service.TeachingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
@Component
public class generator {
    private static final Random RANDOM = new Random();
    private final TeachingRecordService teachingRecordService;

    @Autowired
    public generator(TeachingRecordService teachingRecordService) {
        this.teachingRecordService = teachingRecordService;
    }
    public  List<Student> generateStudents(int year, int count) {
        Faker faker = new Faker(Locale.CHINA);
        Random random = new Random();
        List<Student> students = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            String studentId = String.format("%d%04d", year, i);
            String studentName = faker.name().fullName();
            Character gender = random.nextBoolean() ? 'm' : 'f';
            String major = random.nextBoolean() ? "计算机科学" : random.nextBoolean() ? "软件工程" : "信息安全";
            Boolean isActive = true;
            students.add(new Student(studentId, studentName, gender, major, isActive));
        }
        System.out.println(students);
        return students;
    }

    public  List<Teacher> generateTeachers(int year, int count) {
        Faker faker = new Faker(Locale.CHINA);
        List<Teacher> teachers = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            String teacherId = String.format("1%04d%02d", year, i);
            Name name = faker.name();
            String teacherName = name.fullName();
            Boolean isActive = true;
            Teacher teacher = new Teacher();
            teacher.setTeacherId(Integer.parseInt(teacherId));
            teacher.setTeacherName(teacherName);
            teacher.setIsActive(isActive);
            teachers.add(teacher);
        }
        return teachers;
    }

    public List<TeachingRecord> generateTeachingRecords() {
        List<TeachingRecord> records = new ArrayList<>();
        for (int courseId = 2; courseId <= 5; courseId++) {
            Set<Integer> teacherIds = new HashSet<>();
            while (teacherIds.size() < 2) {
                int teacherId = 1202201 + RANDOM.nextInt(6); // 1202201 到 1202206
                teacherIds.add(teacherId);
            }
            Iterator<Integer> iterator = teacherIds.iterator();
            for (int i = 0; i < 2; i++) { // 每门课至少两个老师
                TeachingRecord record = new TeachingRecord();
                record.setCourseId(courseId);
                record.setTeacherId(iterator.next()); // 1202201 到 1202206
                record.setSemester("秋");
                record.setYear(2024);
                record.setGradeComposition(generateGradeComposition());
                records.add(record);
            }
        }
        return records;
    }
    public  List<GradeDetail> generateGradeDetail() {
        List<GradeDetail> records = new ArrayList<>();
        for (int enrollmentId = 1801; enrollmentId <= 2502; enrollmentId++) {
            GradeDetail record = new GradeDetail();
            Map<String, Integer> gradeMap = new HashMap<>();
            gradeMap.put("lab_score", generateRandomScore());
            gradeMap.put("final_score", generateRandomScore());
            gradeMap.put("midterm_score", generateRandomScore());
            gradeMap.put("regular_score", generateRandomScore());
            record.setEnrollmentId(enrollmentId);
            record.setGradeDetail(new Gson().toJson(gradeMap));
            records.add(record);
        }
        return records;
    }

    private  int generateRandomScore() {
        return 55 + RANDOM.nextInt(46); // 生成 55 到 100 之间的随机数
    }
    private  String generateGradeComposition() {
        double[] weights = new double[4];
        double sum = 0.0;

        // 生成四个随机数
        for (int i = 0; i < 4; i++) {
            weights[i] = RANDOM.nextDouble();
            sum += weights[i];
        }

        // 归一化，使总和为1
        for (int i = 0; i < 4; i++) {
            weights[i] /= sum;
            weights[i] = round(weights[i], 2);
        }
        // 调整权重以确保总和为1
        adjustWeights(weights);
        Map<String, Double> gradeMap = new HashMap<>();
        gradeMap.put("lab_score", weights[0]);
        gradeMap.put("final_score", weights[1]);
        gradeMap.put("midterm_score", weights[2]);
        gradeMap.put("regular_score", weights[3]);
        return new Gson().toJson(gradeMap);
    }

    private  void adjustWeights(double[] weights) {
        double sum = 0.0;
        for (double weight : weights) {
            sum += weight;
        }

        if (Math.abs(sum - 1.0) > 0.01) { // 如果总和不为1，进行微调
            double diff = 1.0 - sum;
            int index = RANDOM.nextInt(4);
            weights[index] += diff;
            weights[index] = round(weights[index], 2);
        }
    }

    private  double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public List<EnrollmentRecord> generateEnrollmentRecords() {
        List<EnrollmentRecord> enrollmentRecords = new ArrayList<>();
        for (int studentId = 20220001; studentId <= 20220200; studentId++) {
            int numberOfCourses = 3 + RANDOM.nextInt(2); // 至少三门课程，最多六门课程
            Set<Integer> teachingIds = new HashSet<>();
            Set<Integer> courseIds = new HashSet<>();
            while (teachingIds.size() < numberOfCourses) {
                int teachingId = 19 + RANDOM.nextInt(8); // 19 到 26
                TeachingRecord teachingRecord = teachingRecordService.getById(teachingId);
                if (teachingRecord != null && !courseIds.contains(teachingRecord.getCourseId())) {
                    teachingIds.add(teachingId);
                    courseIds.add(teachingRecord.getCourseId());
                }
            }
            for (Integer teachingId : teachingIds) {
                EnrollmentRecord record = new EnrollmentRecord();
                record.setStudentId(studentId);
                record.setTeachingId(teachingId);
                record.setTotalGrade(null); // total_grade 暂时保持为 null
                enrollmentRecords.add(record);
            }
        }
        return enrollmentRecords;
    }

}
