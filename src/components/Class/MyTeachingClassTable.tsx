// MyTeachingClassTable.tsx
import React from 'react';
import BaseTable from '../Table/BaseTable';

interface ClickableTableProps {
    columns: any[];
    data: any[];
    onRowClick: (rowData: Record<string, any>) => void; // 必须传入行点击事件处理函数
}

const MyTeachingClassTable: React.FC<ClickableTableProps> = ({ columns, data, onRowClick }) => {
    // const teachingIdAccessor=columns.find((col)=>col.Header==="授课记录 Id")?.accessor;
    // // 定义一个新的回调函数，用于传递 teachingId 的值
    // const handleRowClick = (row: any) => {
    //     if (onRowClick && teachingIdAccessor) {
    //         onRowClick(row[teachingIdAccessor]);
    //     }
    // };
    return <BaseTable columns={columns} data={data} onRowClick={ onRowClick} />;
};

export default MyTeachingClassTable;
