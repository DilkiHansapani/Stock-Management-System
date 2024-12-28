import * as XLSX from "xlsx";

export const generateExcelReport = (reportType, tableData, columns) => {
  const worksheetData = [
    columns.map((col) => col.title),
    ...tableData.map((row) => columns.map((col) => row[col.dataIndex])),
  ];

  const worksheet = XLSX.utils.aoa_to_sheet(worksheetData);
  const workbook = XLSX.utils.book_new();
  XLSX.utils.book_append_sheet(workbook, worksheet, "Report");

  XLSX.writeFile(workbook, `${reportType}_Report.xlsx`);
};
