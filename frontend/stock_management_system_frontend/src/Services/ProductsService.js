import api from "../APIs/AxiosInstance";

export const fetchMaterials = async ({ materialSearch, pagination }) => {
  const params = {
    searchTerm: materialSearch,
    page: pagination?.page || 0,
    size: pagination?.size || 10,
  };

  const response = await api.get("/materials", { params });
  return response.data;
};

export const getAllMaterials = async () => {
  const response = await api.get("materials/all");
  return response;
};

export const addMaterial = async (materialData) => {
  const response = await api.post("/materials", materialData);
  return response.data;
};

export const updateMaterial = async (materialId, updatedData) => {
  const response = await api.put(`/materials/${materialId}`, updatedData);
  return response.data;
};

export const fetchCategories = async ({ categorySearch, pagination }) => {
  const params = {
    searchTerm: categorySearch,
    page: pagination?.page || 0,
    size: pagination?.size || 10,
  };
  const response = await api.get("/categories", { params });
  return response.data;
};

export const getAllCategories = async () => {
  const response = await api.get("categories/all");
  return response;
};

export const addCategory = async (categoryData) => {
  const response = await api.post("/categories", categoryData);
  return response.data;
};
