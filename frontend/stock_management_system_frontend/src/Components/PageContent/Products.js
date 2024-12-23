import React, { useState } from "react";
import { Input, Button, Form, Card, notification } from "antd";
import TableComponent from "../Common/TableComponent";
import {
  addMaterial as apiAddMaterial,
  fetchMaterials,
  addCategory as apiAddCategory,
  fetchCategories,
} from "../../Services/ProductsService";

const Products = () => {
  const [materials, setMaterials] = useState([]);
  const [categories, setCategories] = useState([]);
  const [materialSearch, setMaterialSearch] = useState("");
  const [categorySearch, setCategorySearch] = useState("");
  const [loading, setLoading] = useState(false); // Loading state

  const [materialForm] = Form.useForm();
  const [categoryForm] = Form.useForm();

  const handleAddMaterial = async (values) => {
    setLoading(true); // Set loading to true
    try {
      console.log("values :", values);
      const newMaterial = await apiAddMaterial(values);
      setMaterials((prevMaterials) => [...prevMaterials, newMaterial]);
      materialForm.resetFields();
      notification.success({ message: "Material added successfully!" });
    } catch (error) {
      notification.error({ message: "Failed to add material." });
    } finally {
      setLoading(false); // Set loading to false
    }
  };

  const handleAddCategory = async (values) => {
    setLoading(true); // Set loading to true
    try {
      const newCategory = await apiAddCategory(values);
      setCategories((prevCategories) => [...prevCategories, newCategory]);
      categoryForm.resetFields();
      notification.success({ message: "Category added successfully!" });
    } catch (error) {
      notification.error({ message: "Failed to add category." });
    } finally {
      setLoading(false); // Set loading to false
    }
  };

  const handleMaterialSearch = (value) => {
    setMaterialSearch(value);
  };

  const handleCategorySearch = (value) => {
    setCategorySearch(value);
  };

  const filteredMaterials = materials.filter(
    (material) =>
      material.materialName
        .toLowerCase()
        .includes(materialSearch.toLowerCase()) ||
      material.materialType.toLowerCase().includes(materialSearch.toLowerCase())
  );

  const filteredCategories = categories.filter((category) =>
    category.categoryType.toLowerCase().includes(categorySearch.toLowerCase())
  );

  const materialColumns = [
    { title: "Material Name", dataIndex: "materialName", key: "materialName" },
    { title: "Material Type", dataIndex: "materialType", key: "materialType" },
  ];

  const categoryColumns = [
    { title: "Category Type", dataIndex: "categoryType", key: "categoryType" },
  ];

  return (
    <div>
      <h2>Manage Product Component</h2>
      <div style={{ display: "flex", gap: "20px" }}>
        <Card
          title="Materials"
          style={{
            flex: 2,
            border: "1px solid #d9d9d9", // Light gray border
            boxShadow: "0 4px 8px rgba(0, 0, 0, 0.1)", // Subtle shadow
          }}
        >
          <Form
            form={materialForm}
            onFinish={handleAddMaterial}
            layout="inline"
          >
            <Form.Item
              name="materialName"
              rules={[
                { required: true, message: "Please input material name!" },
              ]}
              style={{ flex: 1 }}
            >
              <Input placeholder="Material Name" />
            </Form.Item>
            <Form.Item
              name="materialType"
              rules={[
                { required: true, message: "Please input material type!" },
              ]}
              style={{ flex: 1 }}
            >
              <Input placeholder="Material Type" />
            </Form.Item>
            <Form.Item>
              <Button type="primary" htmlType="submit" loading={loading}>
                Add Material
              </Button>
            </Form.Item>
          </Form>

          <Input.Search
            placeholder="Search Materials"
            allowClear
            enterButton="Search"
            size="large"
            onSearch={handleMaterialSearch}
            style={{ marginTop: "20px", marginBottom: "10px", width: "98%" }}
          />

          <TableComponent
            columns={materialColumns}
            data={filteredMaterials}
            loading={false}
            pagination={{ pageSize: 5 }}
          />
        </Card>

        <Card
          title="Categories"
          style={{
            flex: 1,
            border: "1px solid #d9d9d9", // Light gray border
            boxShadow: "0 4px 8px rgba(0, 0, 0, 0.1)", // Subtle shadow
          }}
        >
          <Form
            form={categoryForm}
            onFinish={handleAddCategory}
            layout="inline"
          >
            <Form.Item
              name="categoryType"
              rules={[
                { required: true, message: "Please input category type!" },
              ]}
              style={{ flex: 1 }}
            >
              <Input placeholder="Category Type" />
            </Form.Item>
            <Form.Item>
              <Button type="primary" htmlType="submit" loading={loading}>
                Add Category
              </Button>
            </Form.Item>
          </Form>

          <Input.Search
            placeholder="Search Categories"
            allowClear
            enterButton="Search"
            size="large"
            onSearch={handleCategorySearch}
            style={{ marginTop: "20px", marginBottom: "10px", width: "96%" }}
          />

          <TableComponent
            columns={categoryColumns}
            data={filteredCategories}
            loading={false}
            pagination={{ pageSize: 5 }}
          />
        </Card>
      </div>
    </div>
  );
};

export default Products;
