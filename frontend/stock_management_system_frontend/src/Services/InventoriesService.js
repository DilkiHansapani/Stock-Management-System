import api from "../APIs/AxiosInstance";

export const fetchInventories = async ({ filters, pagination }) => {
  const params = {
    ...filters,
    page: pagination?.page || 1,
    size: pagination?.size || 10,
  };

  const response = await api.get("/inventories", { params });
  return response.data;
};

export const addInventory = async (inventoryData) => {
  const response = await api.post("/inventory", inventoryData);
  return response.data;
};

export const updateInventory = async (inventoryId, updatedData) => {
  const response = await api.put(`/inventory/${inventoryId}`, updatedData);
  return response.data;
};
