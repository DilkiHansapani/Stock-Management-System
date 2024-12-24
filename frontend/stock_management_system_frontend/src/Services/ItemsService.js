import api from "../APIs/AxiosInstance";

export const fetchItems = async ({ filters, pagination }) => {
  const params = {
    ...filters,
    page: pagination?.page || 1,
    size: pagination?.size || 10,
  };

  const response = await api.get("/items", { params });
  return response.data;
};

export const updateItem = async (itemId, updatedData) => {
  const response = await api.put(`/items/${itemId}`, updatedData);
  return response.data;
};
