
'use client'
import React, {useEffect, useState} from 'react';
import fetchWithAuth from '@/lib/api/fetchWithAuth'
import StuSelectedCourseList from '@/components/Course/StuSelectedCourseList';
interface StudentHomeProps {
    userId: string;
}
const StudentHome: React.FC<StudentHomeProps> = ({ userId }) => {
    const [studentData, setStudentData] = useState<any>(null);
    const [loading, setLoading] = useState(true);
    useEffect(() => {
        const fetchStudentData = async () => {
            try {
                const response = await fetchWithAuth(`/api/student/view/${userId}`);
                //const response = await fetchWithAuth(`http://localhost:8080/student/view/${userId}`);
                const data = await response.json();
            setStudentData(data);
            } catch (err) {
                console.log(err as Error);
            } finally {
                setLoading(false);
            }
        };
        fetchStudentData();
    }, [userId]); // 依赖于 userId，当 userId 变化时重新获取数据




    return (
        <div>
            {loading && <p>Loading...</p>}
            <h1>学生首页</h1>
            <p>欢迎来到学生首页！</p>
            <p>你的学号是：{studentData?.studentId}</p>
            <p>你的姓名是：{studentData?.studentName}</p>
            <p>你的性别是：{studentData?.gender}</p>
            <p>你的专业是：{studentData?.major}</p>
            <h2>选课记录</h2>
            <StuSelectedCourseList studentId={userId}/>
        </div>
    );
};

export default StudentHome;
