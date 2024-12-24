import React from "react";
import { Table } from "antd";

const TableComponent = ({
  columns,
  data,
  loading,
  pagination,
  onPaginationChange,
}) => {
  return (
    <Table
      columns={columns}
      dataSource={data}
      loading={loading}
      rowKey="key" // Ensure `key` is unique for each row
      pagination={{
        current: pagination.page,
        pageSize: pagination.size,
        total: pagination.total,
        onChange: onPaginationChange,
      }}
    />
  );
};

export default TableComponent;
