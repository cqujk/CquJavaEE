'use client'
import React, { useEffect, useState } from 'react';
import fetchWithAuth from "@/lib/api/fetchWithAuth";
import BaseTable from "@/components/Table/BaseTable"; // 确保路径正确
interface User {
    userId: number;
    userType: string;
    isActive: boolean;
}
const AccountManagement: React.FC = () => {
    const [users, setUsers] = useState<User[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    useEffect(() => {
        const fetchUsers = async () => {
            try {
                const response = await fetchWithAuth('/api/users/list');
                //console.log('Response:', response);
                if (!response.ok) {
                    throw new Error(`HTTP error! Status: ${response.status}`);
                }
                const data = await response.json();
                setUsers(data.users);
            } catch (err) {
                // setError(err.message);
                alert('操作失败，请重试。');
            } finally {
                setLoading(false);
            }
        };
        fetchUsers();
    }, []);

    const toggleUserActiveStatus = async (userId: number, isActive: boolean) => {
        const endpoint = isActive ? '/auth/logout' : '/auth/recover';
        try {
            const response = await fetch(endpoint, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ userId }),
            });
            //console.log('Response:', response.json());
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
             }
            // const data=await response.json();
            // console.log(data);
            // 更新本地状态以反映更改
            setUsers((prevUsers) =>
                prevUsers.map((user) =>
                    user.userId === userId ? { ...user, isActive: !isActive } : user
                )
            );
        } catch (err) {
            console.error(err);
            alert('操作失败，请重试。');
        }
    };

    // 定义表格列
    const columns = React.useMemo(
        () => [
            {
                Header: 'ID',
                accessor: 'userId',
            },
            {
                Header: 'Type',
                accessor: 'userType',
            },
            {
                Header: '状态',
                accessor: 'isActive',
                Cell: ({ row }: any) => (
                    <span>{row.original.isActive ? '正常' : '冻结'}</span>
                ),
            },
            {
                Header: '操作',
                Cell: ({ row }: any) => (
                    <button
                        onClick={() => toggleUserActiveStatus(row.original.userId, row.original.isActive)}
                        className={`px-2 py-1 rounded ${
                            row.original.isActive ? 'bg-red-500 text-white' : 'bg-green-500 text-white'
                        }`}
                    >
                        {row.original.isActive ? '冻结' : '解冻'}
                    </button>
                ),
            },
        ],
        []
    );
    return (
        <div className="p-4 border rounded-lg shadow-md">
            <h2 className="text-xl font-bold mb-4">账号管理</h2>
            {loading && <p>Loading...</p>}
            {!loading && users.length === 0 && <p>没有用户数据。</p>}
            {!loading && users.length > 0 && (
                <BaseTable
                    columns={columns}
                    data={users}
                />
            )}
        </div>
    );
};

export default AccountManagement;
