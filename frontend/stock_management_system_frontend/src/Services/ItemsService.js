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
