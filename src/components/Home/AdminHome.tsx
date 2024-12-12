'use client'
import React from 'react';
import AllCourseList from "@/components/Course/AllCourseList";
import AllClass from "@/components/Class/AllClass";
const AdminHome: React.FC = () => {
    return (
        <div>
            <h1>管理员首页</h1>
            <p>欢迎来到管理员首页！</p>
            <AllCourseList/>
            <AllClass/>
        </div>
    );
};

export default AdminHome;
