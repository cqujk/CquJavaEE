'use client'
import React, {useEffect, useState} from 'react';
import fetchWithAuth from "@/lib/api/fetchWithAuth";
import TeacherClass from "@/components/Class/TeacherClass";
import {handleLogout} from "@/lib/api/handleLogout";
interface TeacherHomeProps {
    userId: number;
}

const TeacherHome: React.FC<TeacherHomeProps> = ({userId}) => {
    const [teacherData, setTeacherData] = useState<any>(null);
    const [loading, setLoading] = useState(true);
    const [currentTime, setCurrentTime] = useState(new Date());
    const handleLogoutClick = () => {
        if (userId) {
            handleLogout(String(userId));
        } else {
            alert('用户ID未找到，请重新登录。');
        }
    };
    useEffect(() => {
        const timer = setInterval(() => {
            setCurrentTime(new Date());
        }, 1000);

        return () => clearInterval(timer);
    }, []);
    useEffect(() => {
        const fetchTeacherData = async () => {
            try {
                const response = await fetchWithAuth(`/api/teacher/view/${userId}`);
                //const response = await fetchWithAuth(`http://localhost:8080/student/view/${userId}`);
                const data = await response.json();
                setTeacherData(data);
            } catch (err) {
                console.log(err as Error);
            } finally {
                setLoading(false);
            }
        };
        fetchTeacherData();
    }, [userId]); // 依赖于 userId，当 userId 变化时重新获取数据
    function handleImportGrades() {

    }

    return (
        <div className="container mx-auto p-6 bg-white rounded-lg shadow-md mt-8">
            {loading && (
                <p className="text-center text-gray-500 animate-pulse">Loading...</p>
            )}
            <h1 className="text-3xl font-extrabold text-gray-800 mb-4">教师首页</h1>
            {teacherData && (
                <p className="text-xl text-gray-700 mb-4">欢迎回来，{teacherData.teacherName} 教师！</p>
            )}
            <p className="text-gray-600 mb-4">当前时间：{currentTime.toLocaleString()}</p>
            <div className="flex justify-center mt-4 space-x-4">
                <button
                    onClick={handleLogoutClick}
                    className="px-6 py-3 bg-red-500 text-white rounded-lg shadow-md hover:bg-red-600 transition duration-300 ease-in-out"
                >
                    注销
                </button>
                <button
                    onClick={handleImportGrades}
                    className="px-6 py-3 bg-blue-500 text-white rounded-lg shadow-md hover:bg-blue-600 transition duration-300 ease-in-out"
                >
                    导入课程成绩
                </button>
            </div>
            <TeacherClass teacherId={userId}/>
        </div>
    );
};

export default TeacherHome;
