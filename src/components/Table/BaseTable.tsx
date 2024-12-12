import React, {useState} from 'react';
import { useTable, useFilters, useSortBy, usePagination } from 'react-table';

interface BaseTableProps {
    columns: any[];
    data: any[];
    //onRowClick?: (rowId: number | string) => void; // 可选的行点击事件处理函数
    onRowClick?: (rowData: Record<string, any>) => void; // 可选的行点击事件处理函数
}

const BaseTable: React.FC<BaseTableProps> = ({ columns, data, onRowClick }) => {
    const [selectedColumn, setSelectedColumn] = useState<string | null>(null);
    const [searchValue, setSearchValue] = useState<string>('');

    // 根据用户选择的列应用过滤器
    const filteredData = React.useMemo(() => {
        if (!selectedColumn || !searchValue) {
            return data; // 没有选择列或没有输入关键词时，不进行过滤
        }

        return data.filter((row) =>
            String(row[selectedColumn]).toLowerCase().includes(searchValue.toLowerCase())
        );
    }, [data, selectedColumn, searchValue]);

    const {
        getTableProps,
        getTableBodyProps,
        headerGroups,
        prepareRow,
        page, // 分页后的当前页面数据
        canPreviousPage,
        canNextPage,
        pageOptions,
        pageCount,
        gotoPage,
        nextPage,
        previousPage,
        setPageSize,
        state: { pageIndex, pageSize },
    } = useTable(
        {
            columns,
            data: filteredData,
            initialState: { pageIndex: 0 },
        },
        useFilters, // 启用全局搜索
        useSortBy, // 启用排序
        usePagination // 启用分页
    );

    return (
        <div className="bg-auto p-2">
            {/* 搜索栏 */}
            {/* 选择要搜索的列 */}
            <div className="mb-2 flex items-center">
                <select
                    value={selectedColumn || ''}
                    onChange={(e) => setSelectedColumn(e.target.value)}
                    className="mr-1 p-1 border rounded bg-auto text-balance"
                >
                    <option value="" className="text-balance">选择筛选字段</option>
                    {columns.map((column) => (
                        <option key={column.accessor} value={column.accessor} className="text-balance">
                            {column.Header}
                        </option>
                    ))}
                </select>

                {/* 搜索输入框 */}
                <input
                    value={searchValue}
                    onChange={(e) => setSearchValue(e.target.value)}
                    placeholder="Search..."
                    className="p-1 border rounded"
                />
            </div>

            {/* 表格 */}
            <table {...getTableProps()} className="w-full border-t border-b border-gray-300">
                <thead>
                    {headerGroups.map((headerGroup) => (
                        <tr {...headerGroup.getHeaderGroupProps()} className="bg-gray-100">
                            {headerGroup.headers.map((column) => (
                                // 添加排序功能
                                <th
                                    {...column.getHeaderProps(column.getSortByToggleProps())}
                                    className="p-1 text-center border-b border-gray-300"
                                >
                                    {column.render('Header')}
                                    {/* 显示排序图标 */}
                                    <span>
                                        {column.isSorted ? (column.isSortedDesc ? ' 🔽' : ' 🔼') : ''}
                                    </span>
                                </th>
                            ))}
                        </tr>
                    ))}
                </thead>
                <tbody {...getTableBodyProps()}>
                    {page.map((row) => {
                        prepareRow(row);
                        return (
                            <tr
                                {...row.getRowProps()}
                                className={`border-b border-gray-300 ${
                                    onRowClick ? 'cursor-pointer hover:bg-gray-200' : ''
                                }`}
                                onClick={() => {
                                    if (onRowClick) {
                                        onRowClick(row.original); // 假设每行数据都有一个唯一的ID
                                    }
                                }}
                            >
                                {row.cells.map((cell) => (
                                    <td {...cell.getCellProps()} className="p-1 text-center border-r border-gray-300">
                                        {cell.render('Cell')}
                                    </td>
                                ))}
                            </tr>
                        );
                    })}
                </tbody>
            </table>
            {/* 分页控制 */}
            <div className="mt-2 flex justify-between items-center">
                <div>
                    <button
                        onClick={() => gotoPage(0)}
                        disabled={!canPreviousPage}
                        className="px-2 py-1 mr-2 bg-blue-500 text-white rounded disabled:bg-gray-300"
                    >
                        {'<<'}
                    </button>
                    <button
                        onClick={() => previousPage()}
                        disabled={!canPreviousPage}
                        className="px-2 py-1 mr-2 bg-blue-500 text-white rounded disabled:bg-gray-300"
                    >
                        {'<'}
                    </button>
                    <button
                        onClick={() => nextPage()}
                        disabled={!canNextPage}
                        className="px-2 py-1 mr-2 bg-blue-500 text-white rounded disabled:bg-gray-300"
                    >
                        {'>'}
                    </button>
                    <button
                        onClick={() => gotoPage(pageCount - 1)}
                        disabled={!canNextPage}
                        className="px-2 py-1 mr-2 bg-blue-500 text-white rounded disabled:bg-gray-300"
                    >
                        {'>>'}
                    </button>
                </div>
                <div>
                    <span>
                        Page{' '}
                        <strong>
                            {pageIndex + 1} of {pageOptions.length}
                        </strong>{' '}
                    </span>
                    <span>
                        | Go to page:{' '}
                        <input
                            type="number"
                            defaultValue={pageIndex + 1}
                            onChange={(e) => {
                                const page = e.target.value ? Number(e.target.value) - 1 : 0;
                                gotoPage(page);
                            }}
                            className="ml-2 w-16 px-2 py-1 border rounded"
                        />
                    </span>
                    <select
                        value={pageSize}
                        onChange={(e) => {
                            setPageSize(Number(e.target.value));
                        }}
                        className="ml-2 px-2 py-1 border rounded"
                    >
                        {[10, 20, 30, 40, 50].map((pageSize) => (
                            <option key={pageSize} value={pageSize}>
                                Show {pageSize}
                            </option>
                        ))}
                    </select>
                </div>
            </div>
        </div>
    );
};

export default BaseTable;
