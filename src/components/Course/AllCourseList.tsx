// CourseList.tsx
import React, {useEffect, useState} from 'react';
import fetchWithAuth from '@/lib/api/fetchWithAuth'
import BaseTable from "@/components/Table/BaseTable";
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
    const data=React.useMemo(
        ()=>
            courses.map((cls)=>({
                courseId:cls.courseId,
                courseName:cls.courseName,
                credits:cls.credits
            })),
        [courses]
    );
    const column=React.useMemo(
        ()=>[
            {
              Header:'课程编号',
              accessor:'courseId',
            },
            {
                Header: '课程名称',
                accessor: 'courseName',
            },
            {
                Header:'学分',
                accessor: 'credits',
            }
        ],
        []
    );
    if (loading) return <p>Loading...</p>;
    return (
        <div className="p-4 border rounded-lg shadow-md">
            <h2 className="text-xl font-bold mb-4">当前所有开设课程</h2>
            <BaseTable columns={column} data={data}/>
        </div>
    );
};

export default CourseList;
