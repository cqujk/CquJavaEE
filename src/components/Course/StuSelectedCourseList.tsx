'use client'
import React, { useEffect, useState } from 'react';
import fetchWithAuth from '@/lib/api/fetchWithAuth';

interface Course {
  courseName: string;
  credits: number;
  totalGrade: number;
}

interface CourseListProps {
  studentId: string;
}

const StuSelectedCourseList: React.FC<CourseListProps> = ({ studentId }) => {
  const [courses, setCourses] = useState<Course[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchCourses = async () => {
      try {
        const response = await fetchWithAuth(`/api/enrollment-record/selectedCourse/${studentId}`);
        if (!response.ok) {
            console.log('Error:', response.status, response.statusText)
        }
        const data = await response.json();
        setCourses(data.courses);
      } catch (err) {
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchCourses();
  }, [studentId]); // 依赖于 studentId，当 studentId 变化时重新获取数据

  return (
    <div>
      {loading && <p>Loading...</p>}
      {courses.length === 0 ? (
        <p>没有选课记录。</p>
      ) : (
        <table border={1} cellPadding={10} cellSpacing={0}>
          <thead>
            <tr>
              <th>课程名称</th>
              <th>学分</th>
              <th>总成绩</th>
            </tr>
          </thead>
          <tbody>
            {courses.map((course, index) => (
              <tr key={index}>
                <td>{course.courseName}</td>
                <td>{course.credits}</td>
                <td>{course.totalGrade !== 0 ? course.totalGrade : '暂无'}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default StuSelectedCourseList;
