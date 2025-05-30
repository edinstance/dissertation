import { ChevronDownIcon, ChevronUpIcon } from "@heroicons/react/24/outline";
import {
  ColumnDef,
  flexRender,
  getCoreRowModel,
  getFilteredRowModel,
  getPaginationRowModel,
  getSortedRowModel,
  PaginationState,
  useReactTable,
} from "@tanstack/react-table";
import React from "react";
import Pagination from "./Pagination";

interface Props<T extends object> {
  data: T[];
  columns: ColumnDef<T>[];
}

export function Table<T extends object>({ data, columns }: Props<T>) {
  const [pagination, setPagination] = React.useState<PaginationState>({
    pageIndex: 0,
    pageSize: 10,
  });

  const table = useReactTable({
    columns,
    data,
    debugTable: false,
    getCoreRowModel: getCoreRowModel(),
    getSortedRowModel: getSortedRowModel(),
    getFilteredRowModel: getFilteredRowModel(),
    getPaginationRowModel: getPaginationRowModel(),
    onPaginationChange: setPagination,
    state: {
      pagination,
    },
  });

  const totalPages = table.getPageCount();
  const currentPage = table.getState().pagination.pageIndex;

  const setCurrentPage = (page: number) => {
    table.setPageIndex(page);
  };

  return (
    <div className="w-full overflow-x-auto p-2">
      <table className="w-full min-w-max bg-blue-100 dark:bg-blue-900">
        <thead>
          {table.getHeaderGroups().map((headerGroup) => (
            <tr key={headerGroup.id}>
              {headerGroup.headers.map((header) => {
                return (
                  <th
                    key={header.id}
                    colSpan={header.colSpan}
                    className="whitespace-nowrap bg-blue-300 px-4 py-2 text-left dark:bg-blue-800"
                  >
                    <div
                      {...{
                        className: header.column.getCanSort()
                          ? "cursor-pointer select-none flex items-center justify-between"
                          : "flex items-center justify-between",
                        onClick: header.column.getToggleSortingHandler(),
                      }}
                    >
                      <span>
                        {flexRender(
                          header.column.columnDef.header,
                          header.getContext(),
                        )}
                      </span>
                      {header.column.getCanSort() ? (
                        header.column.getIsSorted() === "asc" ? (
                          <ChevronUpIcon className="h-4 w-4" />
                        ) : header.column.getIsSorted() === "desc" ? (
                          <ChevronDownIcon className="h-4 w-4" />
                        ) : (
                          <div className="opacity-25">
                            <ChevronUpIcon className="h-4 w-4" />
                          </div>
                        )
                      ) : null}
                    </div>
                  </th>
                );
              })}
            </tr>
          ))}
        </thead>
        <tbody>
          {table.getRowModel().rows.map((row) => {
            return (
              <tr key={row.id}>
                {row.getVisibleCells().map((cell) => {
                  return (
                    <td key={cell.id} className="whitespace-nowrap px-4 py-2">
                      {flexRender(
                        cell.column.columnDef.cell,
                        cell.getContext(),
                      )}
                    </td>
                  );
                })}
              </tr>
            );
          })}
        </tbody>
      </table>
      <Pagination
        total={totalPages}
        page={currentPage}
        setCurrentPage={setCurrentPage}
      />
    </div>
  );
}
