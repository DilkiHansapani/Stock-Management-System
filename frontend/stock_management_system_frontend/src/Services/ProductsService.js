import api from "../APIs/AxiosInstance";

export const fetchProducts = async ({ filters, pagination }) => {
  const params = {
    ...filters,
    page: pagination?.page || 1,
    size: pagination?.size || 10,
  };

  const response = await api.get("/products", { params });
  return response.data;
};

export const addProduct = async (productData) => {
  const response = await api.post("/products", productData);
  return response.data;
};

export const updateProduct = async (productId, updatedData) => {
  const response = await api.put(`/products/${productId}`, updatedData);
  return response.data;
};
