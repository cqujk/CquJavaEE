'use client'
import React, {useEffect, useState} from 'react';
import StudentHome from '@/components/Home/StudentHome';
import TeacherHome from '@/components/Home/TeacherHome';
import AdminHome from '@/components/Home/AdminHome';

export default function Home()  {
    const [userType, setUserType] = useState<string | null>(null);
    const [userId, setUserId] = useState<string | null>(null);
    useEffect(() => {
        const storedUserType = localStorage.getItem('userType');
        const storedUserId = localStorage.getItem('userId');
        if (storedUserType) {
            setUserType(storedUserType);
        }
        if (storedUserId) {
            setUserId(storedUserId);
        }
    }, []);

    if (!userType || !userId) {
        return <div className="h-screen flex items-center justify-center">Loading...</div>;
    }

    return (
        <div className="h-screen">
            {(() => {
                switch (userType) {
                    case 'STUDENT':
                        return <StudentHome userId={userId} />;
                    case 'TEACHER':
                        return (
                            <div className="container mx-auto p-4 border-2 border-blue-500 h-screen">
                                <TeacherHome userId={Number(userId)} />
                            </div>
                        );
                    case 'ADMIN':
                        return <AdminHome userId={userId} />;
                    default:
                        return <div className="h-screen flex items-center justify-center">未知用户类型</div>;
                }
            })()}
        </div>
    );
};


