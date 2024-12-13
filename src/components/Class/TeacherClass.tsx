import React, { useEffect, useState } from 'react';
import fetchWithAuth from '@/lib/api/fetchWithAuth';
//import { Table, TableHead, TableRow, TableCell, TableBody, TableSortLabel } from '@material-ui/core';
import MyTeachingClassTable from "@/components/Class/MyTeachingClassTable";
import DetailModal from "@/components/Class/ClassDetail";
interface GradeComposition {
    midterm_score: number;
    final_score: number;
    lab_score: number;
    regular_score: number;
}
interface Class {
    teachingId: number;
    courseId: number;
    // teacherId: number;
    courseName: string;
    credits: number;
    year: number;
    // teacherName: string;
    studentCount: number;
    // gradeComposition: GradeComposition;
    semester: string;
}
interface TeacherClassProps {
    teacherId: number;
}

const TeacherClass: React.FC<TeacherClassProps> = ({ teacherId }) => {
    const [classes, setClasses] = useState<Class[]>([]);
    const [loading, setLoading] = useState(true);
    // 弹窗状态
    //const [selectedRowId, setSelectedRowId] = useState<number | string | null>(null);
    const [selectedTeachingId, setSelectedTeachingId] = useState<number | string | null>(null);
    const [isModalOpen, setIsModalOpen] = useState(false);

    //const handleRowClick = (rowId: number | string) => {
    const handleRowClick = (rowData: Record<string, any>) => {
        //setSelectedRowId(rowId);
       // console.log('selectedRowId:', rowData);
        const teachingRecord=rowData['teachingId'];
        setSelectedTeachingId(teachingRecord);
      //  console.log('teachingRecord:',teachingRecord);
        setIsModalOpen(true);
    };

    useEffect(() => {
        const fetchCourses = async () => {
            try {
                const response = await fetchWithAuth(`/api/teaching-record/teacher-class-info?teacherId=${teacherId}`);
                if (!response.ok) {
                    console.log('Error:', response.status, response.statusText);
                    return;
                }
                const data = await response.json();
                const parsedClasses = data.classInfo.map((cls: any) => ({
                    ...cls,
                    gradeComposition: JSON.parse(cls.gradeComposition)
                }));
                setClasses(parsedClasses);
            } catch (err) {
                console.error(err);
            } finally {
                setLoading(false);
            }
        };
        fetchCourses();
    }, [teacherId]);
    const data = React.useMemo(
        () =>
            classes.map((cls) => ({
                teachingId: cls.teachingId,
                courseId: cls.courseId,
                // teacherId: cls.teacherId,
                courseName: cls.courseName,
                credits: cls.credits,
                year: cls.year,
                semester:cls.semester,
                studentCount: cls.studentCount,
            })),
        [classes]
    );
    const columns = React.useMemo(
        () => [
            {
                Header: '授课记录Id',
                accessor: 'teachingId',
            },
            {
                Header: '课程Id',
                accessor: 'courseId'
            },
            {
                Header: '课程名称',
                accessor: 'courseName' ,
            },
            {
                Header: '学分',
                accessor: 'credits' ,
            },
            {
                Header: '年份',
                accessor: 'year' ,
            },
            {
                Header: '学期',
                accessor: 'semester' ,
            },
            {
                Header: '学生数量',
                accessor: 'studentCount' ,
            },
        ],
        []
    );

    if (loading) return <p>Loading...</p>;

    return (
        <div className="container mx-auto p-6 bg-white rounded-lg shadow-md mt-8">
            <h1 className="text-2xl font-bold text-gray-800 mb-4">我的授课</h1>
            <MyTeachingClassTable
                columns={columns}
                data={data}
                onRowClick={handleRowClick}
                className="mb-4" // 添加间距
            />
            <DetailModal
                isOpen={isModalOpen}
                onClose={() => setIsModalOpen(false)}
                teachingId={selectedTeachingId}
            />
        </div>
    );
};

export default TeacherClass;
