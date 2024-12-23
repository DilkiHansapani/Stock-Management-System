import api from "../APIs/AxiosInstance";

export const fetchCategories = async ({ filters, pagination }) => {
  const params = {
    ...filters,
    page: pagination?.page || 1,
    size: pagination?.size || 10,
  };

  const response = await api.get("/categories", { params });
  return response.data;
};

export const addCategory = async (categoryData) => {
  const response = await api.post("/category", categoryData);
  return response.data;
};

export const updateCategory = async (categoryId, updatedData) => {
  const response = await api.put(`/category/${categoryId}`, updatedData);
  return response.data;
};
