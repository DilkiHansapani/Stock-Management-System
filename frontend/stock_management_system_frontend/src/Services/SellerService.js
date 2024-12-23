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

export const addSeller = async (sellerData) => {
  const response = await api.post("/sellers", sellerData);
  console.log("add seller response :", response);
  return response;
};

export const updateSeller = async (sellerId, updatedData) => {
  const response = await api.put(`/sellers/${sellerId}`, updatedData);
  return response.data;
};
