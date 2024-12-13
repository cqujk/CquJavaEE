'use client'
import React, {useEffect, useState} from 'react';
import AllCourseList from "@/components/Course/AllCourseList";
import AllClass from "@/components/Class/AllClass";
import {handleLogout} from "@/lib/api/handleLogout";
import AccountManagement from "@/components/Admin/AccountManagement";
import StudentManagement from "@/components/Admin/StudentManagement";
import fetchWithAuth from "@/lib/api/fetchWithAuth";


const AdminHome: React.FC = () => {
    const [selectedFeature, setSelectedFeature] = useState<string>('账号管理');
    const [currentTime, setCurrentTime] = useState(new Date());
    const userId = localStorage.getItem('userId');
    const handleFeatureChange = (feature: string) => {
        setSelectedFeature(feature);
    };
    const handleLogoutClick = () => {
        if (userId) {
            handleLogout(userId);
        } else {
            alert('用户ID未找到，请重新登录。');
        }
    };

    const handleImportGrades = async () => {
        try {
            const response = await fetchWithAuth('/api/grade-detail/generate', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ /* 如果需要发送数据，可以在这里添加 */ }),
            });

            // if (!response.ok) {
            //     throw new Error(`HTTP error! Status: ${response.status}`);
            // }

            alert('成绩导入成功！');
        } catch (error) {
            console.error('Error importing grades:', error);
            alert('成绩导入失败，请重试。');
        }
    };
    const handleClearGrades = async () => {
        try {
            const response = await fetchWithAuth('/api/grade-detail/clear-all', {
                method: 'DELETE',
            });

            // if (!response.ok) {
            //     throw new Error(`HTTP error! Status: ${response.status}`);
            // }

            alert('所有成绩已重置！');
        } catch (error) {
            console.error('Error clearing grades:', error);
            alert('重置成绩失败，请重试。');
        }
    };
    useEffect(() => {
        const timer = setInterval(() => {
            setCurrentTime(new Date());
        }, 1000);

        return () => clearInterval(timer);
    }, []);
    return (
        <div className="min-h-screen bg-gray-100 flex items-center justify-center p-6">
            <div className="bg-white p-8 rounded-lg shadow-md w-full max-w-4xl">
                <h1 className="text-3xl font-bold mb-4 text-center">管理员首页</h1>
                <p className="text-lg mb-6 text-center">欢迎来到管理员首页！当前时间： {currentTime.toLocaleTimeString()}</p>
                <div className="flex justify-between mb-4">
                    <button
                        onClick={handleLogoutClick}
                        className="px-4 py-2 bg-red-500 text-white rounded-lg shadow-md hover:bg-red-600 transition duration-300"
                    >
                        注销
                    </button>
                    <button
                        onClick={handleImportGrades}
                        className="ml-2 px-4 py-2 bg-blue-500 text-white rounded-lg shadow-md hover:bg-blue-600 transition duration-300"
                    >
                        批量导入成绩
                    </button>
                    <button
                        onClick={handleClearGrades}
                        className="ml-2 px-4 py-2 bg-red-500 text-white rounded-lg shadow-md hover:bg-red-600 transition duration-300"
                    >
                        重置所有成绩
                    </button>
                </div>
                <div className="flex justify-between bg-gray-200 p-2 rounded-t-lg">
                    <button
                        onClick={() => handleFeatureChange('账号管理')}
                        className={`w-full py-2 px-4 text-center rounded-t-lg transition duration-300 ${selectedFeature === '账号管理' ? 'bg-white shadow-md' : 'hover:bg-gray-300'}`}
                    >
                        账号管理
                    </button>
                    <button
                        onClick={() => handleFeatureChange('课程管理')}
                        className={`w-full py-2 px-4 text-center rounded-t-lg transition duration-300 ${selectedFeature === '课程管理' ? 'bg-white shadow-md' : 'hover:bg-gray-300'}`}
                    >
                        课程管理
                    </button>
                    <button
                        onClick={() => handleFeatureChange('班级管理')}
                        className={`w-full py-2 px-4 text-center rounded-t-lg transition duration-300 ${selectedFeature === '班级管理' ? 'bg-white shadow-md' : 'hover:bg-gray-300'}`}
                    >
                        班级管理
                    </button>
                    <button
                        onClick={() => handleFeatureChange('学生管理')}
                        className={`w-full py-2 px-4 text-center rounded-t-lg transition duration-300 ${selectedFeature === '学生管理' ? 'bg-white shadow-md' : 'hover:bg-gray-300'}`}
                    >
                        学生管理
                    </button>
                </div>
                <div className="border-t">
                    {selectedFeature === '账号管理' && <AccountManagement />}
                    {selectedFeature === '课程管理' && <AllCourseList />}
                    {selectedFeature === '班级管理' && <AllClass />}
                    {selectedFeature === '学生管理' && <StudentManagement />}
                </div>
            </div>
        </div>
    );
};

export default AdminHome;
