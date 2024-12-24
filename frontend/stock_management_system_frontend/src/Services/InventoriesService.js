import api from "../APIs/AxiosInstance";

export const fetchInventories = async ({ searchTerm, pagination }) => {
  const params = {
    searchTerm: searchTerm,
    page: pagination?.page || 0,
    size: pagination?.size || 10,
  };

  const response = await api.get("/inventories", { params });
  return response.data;
};

export const addInventory = async (inventoryData) => {
  const response = await api.post("/inventories", inventoryData);
  return response.data;
};

export const updateInventory = async (inventoryId, updatedData) => {
  const response = await api.put(`/inventories/${inventoryId}`, updatedData);
  return response.data;
};
