'use client'
import React, { useEffect, useState } from 'react';
import fetchWithAuth from '@/lib/api/fetchWithAuth';
import BaseTable from "@/components/Table/BaseTable";
import { PieChart, Pie, Cell, Tooltip, BarChart, Bar, XAxis, YAxis, CartesianGrid, Legend } from 'recharts';
interface Course {
  courseName: string;
  credits: number;
  totalGrade: number;
}

interface CourseListProps {
  studentId: string;
}

const StuSelectedCourseList: React.FC<CourseListProps> = ({ studentId }) => {
  const [courses, setCourses] = useState<Course[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchCourses = async () => {
      try {
        const response = await fetchWithAuth(`/api/enrollment-record/selectedCourse/${studentId}`);
        if (!response.ok) {
            console.log('Error:', response.status, response.statusText)
        }
        const data = await response.json();
        setCourses(data.courses);
      } catch (err) {
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchCourses();
  }, [studentId]); // 依赖于 studentId，当 studentId 变化时重新获取数据
  const columns =React.useMemo(
      ()=>[
    { Header: '课程名称', accessor: 'courseName' },
    { Header: '学分', accessor: 'credits' },
    { Header: '总成绩', accessor: 'totalGrade' },
  ],
      []
  );
  const data=React.useMemo(
      ()=>
          courses.map(course=>({
              courseName:course.courseName,
              credits:course.credits,
              totalGrade:course.totalGrade
          })),
      [courses]
  );
  // 图表数据
  const chartDataCredits = data;
  const chartDataGrades = data;

  return (
      <div className="container mx-auto p-6 bg-white rounded-lg shadow-md mt-8">
        {loading && <p>Loading...</p>}
        {courses.length === 0 ? (
            <p>没有选课记录。</p>
        ) : (
            <>
              <div className="flex flex-row space-x-8 mb-8">
                <div>
                  <h3 className="text-lg font-semibold text-gray-700 mb-2">学分分布</h3>
                  <BarChart
                      width={400}
                      height={300}
                      data={chartDataCredits}
                      margin={{
                        top: 5,
                        right: 30,
                        left: 20,
                        bottom: 5,
                      }}
                  >
                    <CartesianGrid strokeDasharray="3 3"/>
                    <XAxis dataKey="name"/>
                    <YAxis/>
                    <Tooltip/>
                    <Legend/>
                    <Bar dataKey="credits" fill="#8884d8"/>
                  </BarChart>
                </div>
                <div>
                  <h3 className="text-lg font-semibold text-gray-700 mb-2">总成绩分布</h3>
                  <BarChart
                      width={400}
                      height={300}
                      data={chartDataGrades}
                      margin={{
                        top: 5,
                        right: 30,
                        left: 20,
                        bottom: 5,
                      }}
                  >
                    <CartesianGrid strokeDasharray="3 3"/>
                    <XAxis dataKey="name"/>
                    <YAxis/>
                    <Tooltip/>
                    <Legend/>
                    <Bar dataKey="totalGrade" fill="#82ca9d"/>
                  </BarChart>
                </div>
              </div>
              <h2 className="text-2xl font-bold text-gray-800 mb-4">我的选课</h2>
              <BaseTable columns={columns} data={data}/>
            </>
        )
        }
      </div>
  )
      ;
};

export default StuSelectedCourseList;
