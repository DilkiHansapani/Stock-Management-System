import api from "../APIs/AxiosInstance";

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
