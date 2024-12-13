import React, {useEffect, useState} from 'react';
import fetchWithAuth from '@/lib/api/fetchWithAuth'
import BaseTable from '@/components/Table/BaseTable';
interface GradeComposition {
    midterm_score: number;
    final_score: number;
    lab_score: number;
    regular_score: number;
}

interface AllClass {
    teachingId: number;
    teacherId: number;
    teacherName: string;
    courseId: number;
    courseName: string;
    year: number;
    semester: string;
    studentCount: number;
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
                setClasses(data.classInfo); // 传递解析后的数据
            } catch (err) {
                console.error(err);
            } finally {
                setLoading(false);
            }
        };
        fetchCourses();
    }, []);
    const data= React.useMemo(
        ()=>
            classes.map((cls)=>({
                courseId:cls.courseId,
                courseName:cls.courseName,
                teachingId:cls.teachingId,
                teacherId:cls.teacherId,
                teacherName:cls.teacherName,
                year:cls.year,
                semester:cls.semester,
                studentCount:cls.studentCount
            })),
        [classes]
    );
    const columns = React.useMemo(
        () => [
            {
                Header:'授课记录编号',
                accessor:'teachingId',
            },
            {
                Header:'教师编号',
                accessor:'teacherId',
            },
            {
                Header:'教师姓名',
                accessor:'teacherName',
            },
            {
                Header:'课程编号',
                accessor:'courseId',
            },
            {
                Header: '课程名称',
                accessor: 'courseName',
            },
            {
                Header: '学年',
                accessor: 'year',
            },
            {
                Header: '学期',
                accessor: 'semester',
            },
            {
                Header: '学生人数',
                accessor: 'studentCount',
            },
        ],
        []
    );
    if (loading) return <p>Loading...</p>;
    return (
        <div className="p-4 border rounded-lg shadow-md">
            <h2 className="text-xl font-bold mb-4">当前所有开课班级</h2>
            <BaseTable columns={columns} data={data}/>
        </div>
    );
}
export default AllClass;
