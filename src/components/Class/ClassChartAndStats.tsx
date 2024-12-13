import React from 'react';
import { PieChart, Pie, Cell, Tooltip, BarChart, Bar, XAxis, YAxis, CartesianGrid, Legend } from 'recharts';

interface ChartAndStatsProps {
    data: any[];
}

const COLORS = ['#0088FE', '#00C49F', '#FFBB28', '#FF8042'];

const ChartAndStats: React.FC<ChartAndStatsProps> = ({ data }) => {
    console.log("Chart get data:  "+data);
    // 计算平均分
    const totalScores = data.reduce((sum, cls) => sum + cls.totalGrade, 0);
    const averageScore = totalScores / data.length;

    // 计算众数
    const scoreCounts: { [key: number]: number } = {};
    data.forEach(cls => {
        const scoreRange = Math.floor(cls.totalGrade / 10) * 10;
        scoreCounts[scoreRange] = (scoreCounts[scoreRange] || 0) + 1;
    });
    const modeScoreRange = Object.keys(scoreCounts).reduce((a, b) => scoreCounts[a] > scoreCounts[b] ? a : b);

    // 计算中位数
    const sortedScores = data.map(cls => cls.totalGrade).sort((a, b) => a - b);
    const midIndex = Math.floor(sortedScores.length / 2);
    const medianScore = sortedScores.length % 2 === 0 ? (sortedScores[midIndex - 1] + sortedScores[midIndex]) / 2 : sortedScores[midIndex];

    // 准备图表数据
    const chartData = Object.entries(scoreCounts).map(([scoreRange, count]) => ({
        name: `${scoreRange}-${parseInt(scoreRange) + 9}`,
        人数: count
    }));

    return (
        <div className="h-full overflow-y-auto pr-4">
            <h3 className="text-lg font-bold mb-2">分数段分布</h3>
            <PieChart width={400} height={400}>
                <Pie
                    data={chartData}
                    cx={200}
                    cy={200}
                    labelLine={false}
                    label={({ name, percent }) => `${name}: ${(percent * 100).toFixed(0)}%`}
                    outerRadius={80}
                    fill="#8884d8"
                    dataKey="人数"
                >
                    {chartData.map((entry, index) => (
                        <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                    ))}
                </Pie>
                <Tooltip />
            </PieChart>
            <BarChart width={400} height={300} data={chartData}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="name" />
                <YAxis />
                <Tooltip />
                <Legend />
                <Bar dataKey="人数" fill="#8884d8" />
            </BarChart>
            <h3 className="text-lg font-bold mt-4">统计数据</h3>
            <p>班级总人数: {data.length}</p>
            <p>平均分: {averageScore.toFixed(2)}</p>
            <p>众数: {modeScoreRange}</p>
            <p>中位数: {medianScore}</p>
        </div>
    );
};

export default ChartAndStats;
