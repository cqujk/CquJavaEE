// CourseList.tsx
import {useEffect, useState} from 'react';
import fetchWithAuth from '@/lib/api/fetchWithAuth'
interface Course {
    courseId: number;
    courseName: string;
    credits: number;
    isActive: boolean;
}

const CourseList: React.FC = () => {
    const [courses, setCourses] = useState<Course[]>([]);
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        const fetchCourses = async () => {
            try {
                const response = await fetchWithAuth('/api/course/list');
                if (!response.ok) {
                    console.log('Error:', response.status, response.statusText)
                }
                const data = await response.json(); // 解析响应为 JSON
                setCourses(data.courses); // 传递解析后的数据
            } catch (err) {
                console.error(err);
            } finally {
                setLoading(false);
            }
        };

        fetchCourses();
    }, []);

    if (loading) return <p>Loading...</p>;
    return (
        <ul>
            {courses.map((course) => (
                <li key={course.courseId}>
                    <h3>{course.courseName}</h3>
                    <p>{course.credits}</p>
                </li>
            ))}
        </ul>
    );
};

export default CourseList;
