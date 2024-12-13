
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
    const [currentTime, setCurrentTime] = useState(new Date());
    useEffect(() => {
        const timer = setInterval(() => {
            setCurrentTime(new Date());
        }, 1000);

        return () => clearInterval(timer);
    }, []);
    useEffect(() => {
        const fetchStudentData = async () => {
            try {
                const response = await fetchWithAuth(`/api/student/${userId}`);
                //const response = await fetchWithAuth(`http://localhost:8080/student/view/${userId}`);
                const data = await response.json();
            setStudentData(data.student);
            } catch (err) {
                console.log(err as Error);
            } finally {
                setLoading(false);
            }
        };
        fetchStudentData();
    }, [userId]); // 依赖于 userId，当 userId 变化时重新获取数据




    return (
        <div className="container mx-auto p-6 bg-white rounded-lg shadow-md mt-8">
            {loading && <p className="text-center text-gray-500">Loading...</p>}
            <h1 className="text-2xl font-bold text-gray-800 mb-4">学生首页</h1>
            {studentData && (
                <div className="space-y-4">
                    <p className="text-gray-700">欢迎来到学生首页！{studentData?.studentId} {studentData?.studentName}</p>
                    <p className="text-gray-700 mb-4">当前时间：{currentTime.toLocaleString()}</p>
                    <div className="bg-gray-100 p-4 rounded-lg shadow-sm flex items-center space-x-4">
                        <span className="text-gray-800 font-semibold">专业：</span>
                        <span className="text-gray-700">{studentData?.major}</span>
                        <span className="text-gray-800 font-semibold">排名：</span>
                        <span className="text-gray-700">{studentData?.totalRank}</span>
                        <span className="text-gray-800 font-semibold">GPA：</span>
                        <span className="text-gray-700">{studentData?.gpa}</span>
                    </div>
                </div>
            )}
            <StuSelectedCourseList studentId={userId}/>
        </div>
    );
};

export default StudentHome;
