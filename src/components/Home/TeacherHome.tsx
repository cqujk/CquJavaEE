'use client'
import React, {useEffect, useState} from 'react';
import fetchWithAuth from "@/lib/api/fetchWithAuth";
import TeacherClass from "@/components/Class/TeacherClass";
interface TeacherHomeProps {
    userId: number;
}

const TeacherHome: React.FC<TeacherHomeProps> = ({userId}) => {
    const [teacherData, setTeacherData] = useState<any>(null);
    const [loading, setLoading] = useState(true);
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
    return (
        <div>
            {loading && <p>Loading...</p>}
            <h1>教师首页</h1>
            <p>欢迎来到教师首页！</p>
            <p>你的用户名是：{teacherData?.teacherName}</p>
            <TeacherClass teacherId={userId}/>
        </div>
    );
};

export default TeacherHome;
