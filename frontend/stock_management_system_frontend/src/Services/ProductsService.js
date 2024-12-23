import api from "../APIs/AxiosInstance";

export const fetchMaterials = async ({ filters, pagination }) => {
  const params = {
    ...filters,
    page: pagination?.page || 0,
    size: pagination?.size || 10,
  };

  const response = await api.get("/materials", { params });
  return response.data;
};

export const addMaterial = async (materialData) => {
  const response = await api.post("/materials", materialData);
  return response.data;
};

export const updateMaterial = async (materialId, updatedData) => {
  const response = await api.put(`/materials/${materialId}`, updatedData);
  return response.data;
};

export const fetchCategories = async ({ filters, pagination }) => {
  const params = {
    ...filters,
    page: pagination?.page || 0,
    size: pagination?.size || 10,
  };

  const response = await api.get("/categories", { params });
  return response.data;
};

export const addCategory = async (categoryData) => {
  const response = await api.post("/categories", categoryData);
  return response.data;
};

export const updateCategory = async (categoryId, updatedData) => {
  const response = await api.put(`/categories/${categoryId}`, updatedData);
  return response.data;
};
