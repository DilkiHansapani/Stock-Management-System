import React, { useState, useEffect } from "react";
import { Input, Button, Form, Card, Table, notification, Modal } from "antd";
import { EditOutlined } from "@ant-design/icons";
import {
  addMaterial,
  fetchMaterials,
  updateMaterial,
  addCategory,
  fetchCategories,
} from "../../Services/ProductsService";
import { CONSTANTS } from "../../Common files/Constants";

const Products = () => {
  const [materials, setMaterials] = useState([]);
  const [categories, setCategories] = useState([]);
  const [materialSearch, setMaterialSearch] = useState("");
  const [categorySearch, setCategorySearch] = useState("");
  const [loading, setLoading] = useState(false);
  const [editingMaterial, setEditingMaterial] = useState(null);
  const [materialPagination, setMaterialPagination] = useState({
    page: 1,
    size: 10,
    total: 0,
  });
  const [categoryPagination, setCategoryPagination] = useState({
    page: 1,
    size: 10,
    total: 0,
  });

  const [materialForm] = Form.useForm();
  const [categoryForm] = Form.useForm();
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [modalForm] = Form.useForm();

  const fetchData = async () => {
    try {
      const materialResponse = await fetchMaterials({
        materialSearch,
        pagination: {
          ...materialPagination,
          page: materialPagination.page - 1,
        },
      });

      const materials = materialResponse.data.content;
      const transformedData = materials.map((material) => ({
        ...material,
        key: materials.materialId,
      }));

      setMaterials(transformedData || []);
      const categoryResponse = await fetchCategories({
        categorySearch,
        pagination: {
          ...categoryPagination,
          page: categoryPagination.page - 1,
        },
      });

      setCategories(categoryResponse.data.content || []);
    } catch (error) {
      notification.error({ message: "Failed to fetch data." });
    }
  };

  useEffect(() => {
    fetchData();
  }, [
    categorySearch,
    materialSearch,
    categoryPagination.page,
    materialPagination.page,
  ]);

  const handleAddMaterial = async (values) => {
    setLoading(true);
    try {
      const materialResponse = await addMaterial(values);
      if (materialResponse.status === CONSTANTS.HttpStatusString.CREATED) {
        materialForm.resetFields();
        notification.success({ message: "Material added successfully!" });
        fetchData();
      }
    } catch (error) {
      notification.error({ message: "Failed to add material." });
    } finally {
      setLoading(false);
    }
  };

  const handleAddCategory = async (values) => {
    setLoading(true);
    try {
      const categoryResponse = await addCategory(values);
      if (categoryResponse.status === CONSTANTS.HttpStatusString.CREATED) {
        categoryForm.resetFields();
        notification.success({ message: "Category added successfully!" });
        fetchData();
      }
    } catch (error) {
      notification.error({ message: "Failed to add category." });
    } finally {
      setLoading(false);
    }
  };

  const handleMaterialSearch = (value) => {
    setMaterialSearch(value);
    fetchData();
  };

  const handleCategorySearch = (value) => {
    setCategorySearch(value);
    fetchData();
  };

  const handleEditMaterial = (record) => {
    setEditingMaterial(record);
    modalForm.setFieldsValue(record);
    setIsModalVisible(true);
  };

  const handleUpdateMaterial = async (values) => {
    setLoading(true);
    try {
      const response = await updateMaterial(editingMaterial.materialId, values);
      if (response.status === CONSTANTS.HttpStatusString.OK) {
        notification.success({ message: "Material updated successfully!" });
        fetchData();
        setEditingMaterial(null);
        materialForm.resetFields();
        setIsModalVisible(false);
      }
    } catch (error) {
      notification.error({ message: "Failed to update material." });
    } finally {
      setLoading(false);
    }
  };

  const materialColumns = [
    {
      title: "Material Name",
      dataIndex: "materialName",
      key: "materialName",
      sorter: (a, b) => a.materialName.localeCompare(b.materialName),
    },
    {
      title: "Material Type",
      dataIndex: "materialType",
      key: "materialType",
      sorter: (a, b) => a.materialType.localeCompare(b.materialType),
    },
    {
      title: "Action",
      key: "action",
      render: (_, record) => (
        <Button
          icon={<EditOutlined />}
          onClick={() => handleEditMaterial(record)}
        >
          Edit
        </Button>
      ),
    },
  ];

  const categoryColumns = [
    {
      title: "Category Type",
      dataIndex: "categoryType",
      key: "categoryType",
      sorter: (a, b) => a.categoryType.localeCompare(b.categoryType),
    },
  ];

  const handleTableChange = (pagination, filters, sorter) => {
    fetchData(pagination, filters, sorter);
  };

  return (
    <div>
      <h2 style={{ marginTop: "15px" }}>Manage Product Component</h2>
      <div style={{ display: "flex", gap: "20px" }}>
        <Card
          title="Materials"
          style={{
            flex: 2,
            border: "1px solid #d9d9d9",
            boxShadow: "0 4px 8px rgba(0, 0, 0, 0.1)",
          }}
        >
          <Form
            form={materialForm}
            onFinish={
              editingMaterial ? handleUpdateMaterial : handleAddMaterial
            }
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
                Add Category
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

          <Table
            columns={materialColumns}
            dataSource={materials}
            rowKey="materialId"
            loading={loading}
            pagination={{ pageSize: 5 }}
            onChange={handleTableChange}
          />
          <Modal
            title="Edit Material"
            visible={isModalVisible}
            footer={null}
            onCancel={() => setIsModalVisible(false)}
          >
            <Form
              form={modalForm}
              onFinish={handleUpdateMaterial}
              layout="vertical"
            >
              <Form.Item
                name="materialName"
                rules={[
                  { required: true, message: "Please input material name!" },
                ]}
              >
                <Input placeholder="Material Name" />
              </Form.Item>
              <Form.Item
                name="materialType"
                rules={[
                  { required: true, message: "Please input material type!" },
                ]}
              >
                <Input placeholder="Material Type" />
              </Form.Item>
              <Form.Item>
                <Button type="primary" htmlType="submit" loading={loading}>
                  Update Material
                </Button>
              </Form.Item>
            </Form>
          </Modal>
        </Card>

        <Card
          title="Categories"
          style={{
            flex: 1,
            border: "1px solid #d9d9d9",
            boxShadow: "0 4px 8px rgba(0, 0, 0, 0.1)",
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
            style={{ marginTop: "20px", marginBottom: "10px", width: "98%" }}
          />

          <Table
            columns={categoryColumns}
            dataSource={categories}
            rowKey="categoryId"
            loading={loading}
            pagination={{ pageSize: 5 }}
            onChange={handleTableChange}
          />
        </Card>
      </div>
    </div>
  );
};

export default Products;
