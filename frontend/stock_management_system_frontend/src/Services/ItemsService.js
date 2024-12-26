import api from "../APIs/AxiosInstance";

export const fetchItems = async ({
  searchItemCode,
  startDate,
  endDate,
  searchStatus,
  pagination,
}) => {
  const params = {
    itemCode: searchItemCode,
    startDateTime: startDate,
    endDateTime: endDate,
    status: searchStatus,
    page: pagination?.page || 0,
    size: pagination?.size || 10,
  };

  const response = await api.get("/items", { params });
  return response.data;
};

export const updateItem = async (itemId, updatedData) => {
  const response = await api.put(`/items/${itemId}`, updatedData);
  return response.data;
};

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
