import api from "../APIs/AxiosInstance";

export const fetchSellers = async ({ filters, pagination }) => {
  const params = {
    searchTerm: filters,
    page: pagination?.page || 0,
    size: pagination?.size || 10,
  };

  const response = await api.get("/sellers", { params });
  return response.data;
};

export const getAllSellers = async () => {
  const response = await api.get("sellers/all");
  return response;
};

export const addSeller = async (sellerData) => {
  const response = await api.post("/sellers", sellerData);
  return response;
};

export const updateSeller = async (sellerId, updatedData) => {
  const response = await api.put(`/sellers/${sellerId}`, updatedData);
  return response.data;
};
