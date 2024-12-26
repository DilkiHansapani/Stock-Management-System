import React, { useState, useEffect } from "react";
import { Select, DatePicker, Button, Table, Card, Row, Col, Radio } from "antd";
import {
  sellerDemand,
  findItemsByDateRange,
  findItemsByStatus,
} from "../../Services/ItemsService";
import { formatDateRange } from "../../Utils/dateUtils";
import { CONSTANTS } from "../../Common files/Constants";
import { generatePdfReport } from "../Common/PdfReportGenerator";
import { generateExcelReport } from "../Common/ExcelReportGenerator";

const { RangePicker } = DatePicker;

const Reports = () => {
  const [reportType, setReportType] = useState(null);
  const [startDate, setStartDate] = useState();
  const [endDate, setEndDate] = useState();
  const [showResults, setShowResults] = useState(false);
  const [tableData, setTableData] = useState([]);
  const [status, setStatus] = useState(null);
  const [columns, setColumns] = useState([]);

  const handleReportTypeChange = (value) => {
    setReportType(value);
    setShowResults(false);
    setTableData([]);
    setStatus(null);
    setStartDate(null);
    setEndDate(null);

    if (value === CONSTANTS.ReportDropdownValues.SELLERS) {
      fetchData(CONSTANTS.ReportDropdownValues.SELLERS).then(() => {
        setShowResults(true);
      });
    }
  };

  const handleStatusChange = (e) => {
    setStatus(e.target.value);

    if (
      reportType === CONSTANTS.ReportDropdownValues.STATUS &&
      e.target.value
    ) {
      fetchData(CONSTANTS.ReportDropdownValues.STATUS);
    }
  };

  const handleDateChange = (dates) => {
    if (dates && dates.length === 2) {
      const { formattedStart, formattedEnd } = formatDateRange(dates);
      setStartDate(formattedStart);
      setEndDate(formattedEnd);

      if (reportType === CONSTANTS.ReportDropdownValues.DATERANGE) {
        fetchData(CONSTANTS.ReportDropdownValues.DATERANGE);
      }
    } else {
      setStartDate(null);
      setEndDate(null);
    }
  };

  const fetchData = async (type) => {
    try {
      let result = [];
      let formattedData = [];
      let columns = [];

      if (type === CONSTANTS.ReportDropdownValues.SELLERS) {
        result = await sellerDemand();
        formattedData = result.data.map((item, index) => ({
          key: index,
          sellerName: item.sellerName,
          count: item.count,
        }));
        columns = [
          { title: "Seller Name", dataIndex: "sellerName", key: "sellerName" },
          { title: "Count", dataIndex: "count", key: "count" },
        ];
      } else if (
        type === CONSTANTS.ReportDropdownValues.DATERANGE &&
        startDate &&
        endDate
      ) {
        result = await findItemsByDateRange(startDate, endDate);
        formattedData = result.data.map((item, index) => ({
          key: index,
          itemCode: item.itemCode,
          dateTime: item.dateTime,
          category: item.inventory.category.categoryType,
          buyingPrice: item.buyingPrice,
          sellingPrice: item.sellingPrice,
          profitPercentage: item.profitPercentage,
        }));
        columns = [
          { title: "Item Code", dataIndex: "itemCode", key: "itemCode" },
          { title: "Date", dataIndex: "dateTime", key: "dateTime" },
          { title: "Category", dataIndex: "category", key: "category" },
          {
            title: "Buying Price(Rs.)",
            dataIndex: "buyingPrice",
            key: "buyingPrice",
          },
          {
            title: "Selling Price(Rs.)",
            dataIndex: "sellingPrice",
            key: "sellingPrice",
          },
          {
            title: "Profit Percentage(%)",
            dataIndex: "profitPercentage",
            key: "profitPercentage",
          },
        ];
      } else if (type === CONSTANTS.ReportDropdownValues.STATUS && status) {
        result = await findItemsByStatus(status);
        formattedData = result.data.map((item, index) => ({
          key: index,
          itemCode: item.itemCode,
          dateTime: item.dateTime,
          category: item.inventory.category.categoryType,
          buyingPrice: item.buyingPrice,
          sellingPrice: item.sellingPrice,
          profitPercentage: item.profitPercentage,
          salePercentage: item.salePercentage,
          status: item.status,
        }));
        columns = [
          { title: "Item Code", dataIndex: "itemCode", key: "itemCode" },
          { title: "Date", dataIndex: "dateTime", key: "dateTime" },
          { title: "Category", dataIndex: "category", key: "category" },
          {
            title: "Buying Price(Rs.)",
            dataIndex: "buyingPrice",
            key: "buyingPrice",
          },
          {
            title: "Selling Price(Rs.)",
            dataIndex: "sellingPrice",
            key: "sellingPrice",
          },
          {
            title: "Profit Percentage(%)",
            dataIndex: "profitPercentage",
            key: "profitPercentage",
          },
          {
            title: "Sale Percentage(%)",
            dataIndex: "salePercentage",
            key: "salePercentage",
          },
          { title: "Status", dataIndex: "status", key: "status" },
        ];
      }

      setTableData(formattedData);
      setColumns(columns);
      setShowResults(true);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  useEffect(() => {
    if (
      reportType === CONSTANTS.ReportDropdownValues.DATERANGE &&
      startDate &&
      endDate
    ) {
      fetchData(CONSTANTS.ReportDropdownValues.DATERANGE);
    } else if (reportType === CONSTANTS.ReportDropdownValues.STATUS && status) {
      fetchData(CONSTANTS.ReportDropdownValues.STATUS);
    }
  }, [reportType, startDate, endDate, status]);

  const generateReport = (format) => {
    if (format === "PDF") {
      const reportData = {
        reportType,
        startDate,
        endDate,
        status,
        data: tableData,
      };
      generatePdfReport(reportType, tableData, columns);
    } else if (format === "Excel") {
      generateExcelReport(reportType, tableData, columns);
    }
  };

  const getPreviousMonth = () => {
    const currentDate = new Date();
    const previousMonthDate = new Date(
      currentDate.getFullYear(),
      currentDate.getMonth() - 1,
      1
    );
    const month = previousMonthDate.toLocaleString("default", {
      month: "long",
    });
    const year = previousMonthDate.getFullYear();
    return `${month} ${year}`;
  };

  const previousMonth = getPreviousMonth();

  return (
    <div style={{ padding: "20px" }}>
      <h2 style={{ marginBottom: "20px", marginTop: "-15px" }}>
        Generate Reports
      </h2>
      <div
        style={{
          marginBottom: "20px",
          display: "flex",
          alignItems: "center",
        }}
      >
        <Select
          style={{ width: "300px", marginRight: "20px" }}
          placeholder="Select Report Type"
          onChange={handleReportTypeChange}
          options={[
            {
              value: CONSTANTS.ReportDropdownValues.SELLERS,
              label: "Soldout Items base on sellers Report",
            },
            {
              value: CONSTANTS.ReportDropdownValues.DATERANGE,
              label: "Soldout Items base on date range Report",
            },
            {
              value: CONSTANTS.ReportDropdownValues.STATUS,
              label: "Items base status Report",
            },
          ]}
        />

        {reportType === CONSTANTS.ReportDropdownValues.STATUS && (
          <Radio.Group
            onChange={handleStatusChange}
            value={status}
            style={{
              display: "flex",
              flexDirection: "column",
              marginTop: "10px",
            }}
          >
            <Radio value="normal">Normal</Radio>
            <Radio value="sale">Sale</Radio>
            <Radio value="stockClearing">Stock Clearing</Radio>
            <Radio value="soldout">Soldout</Radio>
          </Radio.Group>
        )}
      </div>

      {reportType === CONSTANTS.ReportDropdownValues.DATERANGE && (
        <RangePicker onChange={handleDateChange} />
      )}

      {showResults && (
        <div>
          <div style={{ marginBottom: "20px" }}>
            <Button
              type="primary"
              style={{ marginRight: "10px" }}
              onClick={() => generateReport("PDF")}
            >
              Generate PDF
            </Button>
            <Button type="default" onClick={() => generateReport("Excel")}>
              Generate Excel
            </Button>
          </div>
          <Table columns={columns} dataSource={tableData} />
        </div>
      )}
    </div>
  );
};

export default Reports;
