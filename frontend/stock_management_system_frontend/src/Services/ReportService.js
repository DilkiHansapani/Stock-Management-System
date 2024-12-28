import api from "../APIs/AxiosInstance";

export const sellerDemand = async () => {
  const response = await api.get("/items/reports/sold-items/by-seller");
  return response.data;
};

export const findItemsByDateRange = async (startDate, endDate) => {
  const params = { startDate: startDate, endDate: endDate };
  const response = await api.get("/items/reports/sold-items/date-range", {
    params,
  });
  return response.data;
};

export const findItemsByStatus = async (status) => {
  const params = { status: status };
  const response = await api.get("/items/reports/status", { params });
  return response.data;
};

export const generateReports = async (
  reportType,
  startDate,
  endDate,
  status
) => {
  const params = {
    reportType: reportType,
    startDate: startDate,
    endDate: endDate,
    status: status,
  };
  const response = await api.get("/reports", { params });
  return response.data;
};
