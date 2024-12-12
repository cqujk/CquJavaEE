// ClickableTable.tsx
import React from 'react';
import BaseTable from './BaseTable';

interface ClickableTableProps {
    columns: any[];
    data: any[];
    onRowClick: (rowId: number | string) => void; // 必须传入行点击事件处理函数
}

const ClickableTable: React.FC<ClickableTableProps> = ({ columns, data, onRowClick }) => {
    return <BaseTable columns={columns} data={data} onRowClick={onRowClick} />;
};

export default ClickableTable;
