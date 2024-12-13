'use client'
import React, { useEffect, useState } from 'react';
import BaseTable from "@/components/Table/BaseTable"; // 确保路径正确
import fetchWithAuth from "@/lib/api/fetchWithAuth";

interface Student {
    studentId: number;
    studentName: string;
    gender: number;
    major: string;
    gpa: number;
    totalRank: number;
}

const StudentManagement: React.FC = () => {
    const [students, setStudents] = useState<Student[]>([]);
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        const fetchStudents = async () => {
            try {
                const response = await fetchWithAuth('/api/student/all-stu');
                if (!response.ok) {
                    throw new Error(`HTTP error! Status: ${response.status}`);
                }
                const data = await response.json();
                const parsedData = data.students.map((cls: any) => ({
                    ...cls,
                    gender:cls.gender==="f"?"女":"男",
                }))
                setStudents(parsedData);
            } catch (err) {
                alert('操作失败，请重试。');
            } finally {
                setLoading(false);
            }
        };

        fetchStudents();
    }, []);

    // 定义表格列
    const columns = React.useMemo(
        () => [
            {
                Header: '学号',
                accessor: 'studentId',
            },
            {
                Header: '姓名',
                accessor: 'studentName',
            },
            {
                Header: '专业',
                accessor: 'major',
            },
            {
                Header: '性别',
                accessor: 'gender',
            },
            {
                Header: 'GPA',
                accessor: 'gpa',
            },
            {
                Header: '排名',
                accessor: 'totalRank',
            },
        ],
        []
    );

    return (
        <div className="p-4 border rounded-lg shadow-md">
            <h2 className="text-xl font-bold mb-4">学生管理</h2>
            {loading && <p>Loading...</p>}
            {!loading && students.length === 0 && <p>没有学生数据。</p>}
            {!loading && students.length > 0 && (
                <BaseTable
                    columns={columns}
                    data={students}
                />
            )}
        </div>
    );
};

export default StudentManagement;
