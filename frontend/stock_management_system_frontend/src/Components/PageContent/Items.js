import React, { useState, useEffect } from "react";
import {
  Table,
  Input,
  Select,
  DatePicker,
  Space,
  message,
  InputNumber,
} from "antd";
import { fetchItems, updateItem } from "../../Services/ItemsService";
import { formatDateRange } from "../../Utils/dateUtils";
import { status } from "../../Common files/Constants";
import moment from "moment";

const { RangePicker } = DatePicker;

const Items = () => {
  const [items, setItems] = useState([]);
  const [searchItemCode, setsearchItemCode] = useState();
  const [searchStatus, setsearchStatus] = useState();
  const [startDate, setStartDate] = useState();
  const [endDate, setEndDate] = useState();
  const [pagination, setPagination] = useState({ page: 1, size: 10, total: 0 });
  const [editingItem, setEditingItem] = useState(null);

  const fetchData = async () => {
    try {
      const response = await fetchItems({
        searchItemCode,
        startDate,
        endDate,
        searchStatus,
        pagination: {
          ...pagination,
          page: pagination.page - 1,
        },
      });

      setItems(response.data.content);
      setPagination({
        page: response.data.pageable.pageNumber + 1,
        size: response.data.pageable.pageSize,
        total: response.data.totalElements,
      });
    } catch (error) {
      message.error("Failed to fetch items.");
    }
  };

  useEffect(() => {
    fetchData();
  }, [searchItemCode, searchStatus, startDate, endDate, pagination.page]);

  const handleItemCodeSearch = (value) => {
    setsearchItemCode(value);
  };

  const handleStatusFilter = (value) => {
    setsearchStatus(value);
  };

  const handleDateRangeChange = (dates) => {
    const { formattedStart, formattedEnd } = formatDateRange(dates);
    console.log("formatedStart :", formattedStart);
    setStartDate(formattedStart);
    setEndDate(formattedEnd);
  };

  const handlePaginationChange = (page, pageSize) => {
    setPagination({
      page,
      size: pageSize,
      total: pagination.total,
    });
  };

  const updateItemOnServer = async (updatedItem) => {
    try {
      const response = await updateItem(updatedItem.itemCode, updatedItem);
      if (response.status === status.HttpStatusString.OK) {
        setItems(response.data);
        fetchData();
        setEditingItem(null);
      }
      console.log("response :", response);
      message.success(`Item ${updatedItem.itemCode} updated successfully.`);
    } catch (error) {
      message.error(`Failed to update item ${updatedItem.itemCode}.`);
    }
  };

  const handleStatusChange = (status, itemCode) => {
    const updatedItems = items.map((item) =>
      item.itemCode === itemCode ? { ...item, status } : item
    );
    setItems(updatedItems);

    const updatedItem = updatedItems.find((item) => item.itemCode === itemCode);

    if (status === "normal") {
      const updatedItemsWithReset = updatedItems.map((item) =>
        item.itemCode === itemCode ? { ...item, salePercentage: 0 } : item
      );
      setItems(updatedItemsWithReset);
      updateItemOnServer({
        itemCode: updatedItem.itemCode,
        sellingPrice: updatedItem.sellingPrice,
        salePercentage: 0,
        status: "normal",
      });
    }
  };

  const handleFieldChange = (value, itemCode, field) => {
    const updatedItems = items.map((item) =>
      item.itemCode === itemCode ? { ...item, [field]: value } : item
    );
    setItems(updatedItems);

    const updatedItem = updatedItems.find((item) => item.itemCode === itemCode);

    if (
      (field === "salePercentage" && updatedItem.status === "sale") ||
      (field === "sellingPrice" && updatedItem.status === "stockClearing")
    ) {
      updateItemOnServer({
        itemCode: updatedItem.itemCode,
        sellingPrice: updatedItem.sellingPrice,
        salePercentage: updatedItem.salePercentage,
        status: updatedItem.status,
      });
    }
  };

  const columns = [
    {
      title: "Item Code",
      dataIndex: "itemCode",
      key: "itemCode",
    },
    {
      title: "Date",
      dataIndex: "dateTime",
      key: "date",
      render: (text) => moment(text).format("YYYY-MM-DD"),
    },
    {
      title: "Category",
      dataIndex: "categoryType",
      key: "category",
    },
    {
      title: "Buying Price (Rs.)",
      dataIndex: "buyingPrice",
      key: "buyingPrice",
      render: (value) => `${value.toFixed(2)}`,
    },
    {
      title: "Selling Price (Rs.)",
      dataIndex: "sellingPrice",
      key: "sellingPrice",
      render: (value, record) =>
        record.status === "stockClearing" || record.status === "sale" ? (
          editingItem &&
          editingItem.itemCode === record.itemCode &&
          editingItem.field === "sellingPrice" ? (
            <InputNumber
              value={value}
              onBlur={() => setEditingItem(null)}
              onPressEnter={(e) =>
                handleFieldChange(
                  e.target.value,
                  record.itemCode,
                  "sellingPrice"
                )
              }
            />
          ) : (
            <span
              onClick={() =>
                setEditingItem({
                  itemCode: record.itemCode,
                  field: "sellingPrice",
                })
              }
            >
              {value.toFixed(2)}
            </span>
          )
        ) : (
          `${value.toFixed(2)}`
        ),
    },
    {
      title: "Profit Percentage",
      dataIndex: "profitPercentage",
      key: "profitPercentage",
      render: (value) => `${value.toFixed(2)}%`,
    },
    {
      title: "Sale Percentage",
      dataIndex: "salePercentage",
      key: "salePercentage",
      render: (value, record) =>
        record.status === "sale" ? (
          editingItem &&
          editingItem.itemCode === record.itemCode &&
          editingItem.field === "salePercentage" ? (
            <InputNumber
              value={value}
              onBlur={() => setEditingItem(null)}
              onPressEnter={(e) =>
                handleFieldChange(
                  e.target.value,
                  record.itemCode,
                  "salePercentage"
                )
              }
            />
          ) : (
            <span
              onClick={() =>
                setEditingItem({
                  itemCode: record.itemCode,
                  field: "salePercentage",
                })
              }
            >
              {value.toFixed(2)}%
            </span>
          )
        ) : (
          `${value.toFixed(2)}%`
        ),
    },
    {
      title: "Status",
      dataIndex: "status",
      key: "status",
      render: (status, record) => (
        <Select
          value={status}
          style={{ width: 120 }}
          onChange={(value) => handleStatusChange(value, record.itemCode)}
        >
          <Select.Option value="normal">Normal</Select.Option>
          <Select.Option value="sale">Sale</Select.Option>
          <Select.Option value="stockClearing">Stock Clearing</Select.Option>
        </Select>
      ),
    },
  ];

  return (
    <div>
      <h2 style={{ marginTop: "-5px" }}>Items</h2>

      <Space style={{ marginBottom: 16 }}>
        <Input.Search
          placeholder="Search by Item Code"
          allowClear
          enterButton="Search"
          onSearch={handleItemCodeSearch}
        />
        <Select
          placeholder="Filter by Status"
          onChange={handleStatusFilter}
          value={searchStatus}
          style={{ width: 200 }}
          allowClear
        >
          <Select.Option value="normal">Normal</Select.Option>
          <Select.Option value="sale">Sale</Select.Option>
          <Select.Option value="stockClearing">Stock Clearing</Select.Option>
        </Select>

        <RangePicker onChange={handleDateRangeChange} />
      </Space>

      <Table
        columns={columns}
        dataSource={Array.isArray(items) ? items : []}
        rowKey="itemCode"
        pagination={{
          current: pagination.page,
          pageSize: pagination.size,
          total: pagination.total,
          onChange: handlePaginationChange,
        }}
      />
    </div>
  );
};

export default Items;
