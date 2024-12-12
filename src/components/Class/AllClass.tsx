import {useEffect, useState} from 'react';
import fetchWithAuth from '@/lib/api/fetchWithAuth'

interface GradeComposition {
    midterm_score: number;
    final_score: number;
    lab_score: number;
    regular_score: number;
}

interface AllClass {
    teachingId: number;
    courseId: number;
    teacherId: number;
    courseName: string;
    credits: number;
    year: number;
    teacherName: string;
    studentCount: number;
    gradeComposition: GradeComposition;
}

const AllClass: React.FC = () => {
    const [classes, setClasses] = useState<AllClass[]>([]);
    const [loading, setLoading] = useState(true);
    useEffect(() => {
        const fetchCourses = async () => {
            try {
                const response = await fetchWithAuth('/api/teaching-record/all-class-info');
                if (!response.ok) {
                    console.log('Error:', response.status, response.statusText);
                    return;
                }

                const data = await response.json(); // 解析响应为 JSON
                console.log(data);
                // 解析 gradeComposition 字符串为对象
                const parsedClasses = data.classInfo.map((cls: any) => ({
                    ...cls,
                    gradeComposition: JSON.parse(cls.gradeComposition)
                }));
                setClasses(parsedClasses); // 传递解析后的数据
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
        <div>
            <h1>All Classes</h1>
            <ul>
                {classes.map((cls) => (
                    <li key={cls.teachingId}>
                        <h3>{cls.courseName}</h3>
                        <p>Credits: {cls.credits}</p>
                        <p>Teacher: {cls.teacherName}</p>
                        <p>Student Count: {cls.studentCount}</p>
                        <p>Grade Composition:</p>
                        <ul>
                            <li>Midterm Score: {cls.gradeComposition.midterm_score*100}%</li>
                            <li>Final Score: {cls.gradeComposition.final_score*100}%</li>
                            <li>Lab Score: {cls.gradeComposition.lab_score*100}%</li>
                            <li>Regular Score: {cls.gradeComposition.regular_score*100}%</li>
                        </ul>
                    </li>
                ))}
            </ul>
        </div>
    );
}
export default AllClass;
