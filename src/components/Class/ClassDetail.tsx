import React, { useEffect, useState } from 'react';
import BaseTable from './BaseTable';

interface DetailModalProps {
    isOpen: boolean;
    onClose: () => void;
    rowId: number | string; // 假设每行数据都有一个唯一的ID
}

const DetailModal: React.FC<DetailModalProps> = ({ isOpen, onClose, rowId }) => {
    const [rowData, setRowData] = useState<any[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        if (isOpen) {
            fetchDetailData();
        }
    }, [isOpen, rowId]);

    const fetchDetailData = async () => {
        try {
            const response = await fetch(`xxx/${rowId}`); // 假设URL为 xxx/{rowId}
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            const data = await response.json();
            setRowData(data);
        } catch (error) {
            setError(error.message);
        } finally {
            setLoading(false);
        }
    };

    if (!isOpen) {
        return null;
    }

    const columns = React.useMemo(
        () => [
            {
                Header: 'ID',
                accessor: 'id',
            },
            {
                Header: 'Name',
                accessor: 'name',
            },
            {
                Header: 'Value',
                accessor: 'value',
            },
            // 添加其他列
        ],
        []
    );

    return (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center">
            <div className="bg-white p-6 rounded shadow-lg w-full max-w-3xl">
                <h2 className="text-xl font-bold mb-4">详细信息</h2>
                {loading ? (
                    <div>Loading...</div>
                ) : error ? (
                    <div className="text-red-500">{error}</div>
                ) : rowData && rowData.length > 0 ? (
                    <BaseTable columns={columns} data={rowData} />
                ) : (
                    <div>No data available</div>
                )}
                <button
                    onClick={onClose}
                    className="mt-4 px-4 py-2 bg-red-500 text-white rounded"
                >
                    关闭
                </button>
            </div>
        </div>
    );
};

export default DetailModal;
