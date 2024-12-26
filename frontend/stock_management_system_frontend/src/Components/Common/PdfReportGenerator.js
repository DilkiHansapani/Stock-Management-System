import jsPDF from "jspdf";
import "jspdf-autotable";

export const generatePdfReport = (reportType, tableData, columns) => {
  const doc = new jsPDF();
  doc.setFontSize(16);
  doc.text(`Report: ${reportType}`, 20, 20);

  const tableHeaders = columns.map((col) => col.title);
  const tableRows = tableData.map((row) =>
    columns.map((col) => row[col.dataIndex])
  );

  doc.autoTable({
    head: [tableHeaders],
    body: tableRows,
    startY: 30,
  });

  doc.save(`${reportType}_Report.pdf`);
};
