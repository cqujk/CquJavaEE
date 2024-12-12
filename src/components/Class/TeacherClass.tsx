import React, { useEffect, useState } from 'react';
import fetchWithAuth from '@/lib/api/fetchWithAuth';
//import { Table, TableHead, TableRow, TableCell, TableBody, TableSortLabel } from '@material-ui/core';
import ClickableTable from "@/components/Table/ClickableTable";
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
    const [selectedRowId, setSelectedRowId] = useState<number | string | null>(null);
    const [isModalOpen, setIsModalOpen] = useState(false);

    const handleRowClick = (rowId: number | string) => {
        setSelectedRowId(rowId);
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
                // teacherName: cls.teacherName,
                studentCount: cls.studentCount,
                // midtermScore: cls.gradeComposition.midterm_score * 100,
                // finalScore: cls.gradeComposition.final_score * 100,
                // labScore: cls.gradeComposition.lab_score * 100,
                // regularScore: cls.gradeComposition.regular_score * 100,
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
            // {
            //     Header: 'Teacher ID',
            //     accessor: 'teacherId' as keyof Class,
            //     sortType:'basic'
            // },
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
            // {
            //     Header: 'Teacher Name',
            //     accessor: 'teacherName' as keyof Class,
            //     sortType:'alphanumeric'
            // },
            {
                Header: '学生数量',
                accessor: 'studentCount' ,
            },
            // {
            //     Header: 'Midterm Score (%)',
            //     accessor: 'midtermScore',
            //
            // },
            // {
            //     Header: 'Final Score (%)',
            //     accessor: 'finalScore' ,
            // },
            // {
            //     Header: 'Lab Score (%)',
            //     accessor: 'labScore',
            // },
            // {
            //     Header: 'Regular Score (%)',
            //     accessor: 'regularScore' ,
            // },
        ],
        []
    );
    // const handleRequestSort = (property: keyof Class | 'midtermScore' | 'finalScore' | 'labScore' | 'regularScore') => {
    //     const isAsc = orderBy === property && order === 'asc';
    //     setOrder(isAsc ? 'desc' : 'asc');
    //     setOrderBy(property);
    // };
    //
    // const sortedData = React.useMemo(() => {
    //     return [...data].sort((a, b) => {
    //         const aValue = a[orderBy];
    //         const bValue = b[orderBy];
    //         if (typeof aValue === 'number' && typeof bValue === 'number') {
    //             return (order === 'asc' ? 1 : -1) * (aValue - bValue);
    //         }
    //         return 0;
    //     });
    // }, [data, order, orderBy]);
    //
    // const isAsc = (column: keyof Class | 'midtermScore' | 'finalScore' | 'labScore' | 'regularScore') => {
    //     return orderBy === column && order === 'asc';
    // };

    if (loading) return <p>Loading...</p>;

    return (
        <div>
            <h1>My Teaching Classes</h1>
            {/*<TableComponent columns={columns} data={data} />*/}
            <ClickableTable columns={columns} data={data} onRowClick={handleRowClick}/>
        </div>
    );
};

export default TeacherClass;
