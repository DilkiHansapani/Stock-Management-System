import React, { useEffect, useState } from "react";
import { Table, Form, Select, Input, Button, message, Card } from "antd";
import { getAllSellers } from "../../Services/SellerService";
import {
  getAllCategories,
  getAllMaterials,
} from "../../Services/ProductsService";
import {
  addInventory,
  fetchInventories,
} from "../../Services/InventoriesService";
import { status } from "../../Common files/Constants";

const { Option } = Select;

const Inventories = () => {
  const [form] = Form.useForm();
  const [sellers, setSellers] = useState([]);
  const [materials, setMaterials] = useState([]);
  const [categories, setCategories] = useState([]);
  const [inventories, setInventories] = useState([]);
  const [searchTerm, setsearchTerm] = useState("");
  const [pagination, setPagination] = useState({ page: 1, size: 10, total: 0 });

  const getData = async () => {
    try {
      const sellerResponse = await getAllSellers();
      setSellers(sellerResponse.data.data);

      const categoriesResponse = await getAllCategories();
      setCategories(categoriesResponse.data.data);

      const materialResponse = await getAllMaterials();
      setMaterials(materialResponse.data.data);
    } catch (error) {
      message.error("Failed to data for sellers,categories and materials");
    }
  };

  useEffect(() => {
    getInventories();
    getData();
  }, []);

  useEffect(() => {
    getInventories();
  }, [searchTerm, pagination.page]);

  const getInventories = async () => {
    try {
      const response = await fetchInventories({
        searchTerm,
        pagination: {
          page: pagination.page - 1,
          size: pagination.size,
        },
      });

      const transformedData = response.data.content.map((item) => ({
        inventoryId: item.inventoryId,
        sellerName: item.seller.sellerName,
        materialName: `${item.material.materialName}-${item.material.materialType}`,
        categoryType: item.category.categoryType,
        quantity: item.quantity,
      }));

      setInventories(transformedData);
      setPagination({
        page: response.data.pageable.pageNumber + 1,
        size: response.data.pageable.pageSize,
        total: response.data.totalElements,
      });
    } catch (error) {
      message.error("Failed to fetch inventories.");
    }
  };

  const handleAdd = async (values) => {
    try {
      const updatedValues = {
        ...values,
        buyingPrice: parseFloat(values.buyingPrice),
        profitPercentage: parseFloat(values.profitPercentage),
        quantity: parseInt(values.quantity, 10),
      };

      const response = await addInventory(updatedValues);
      if (response.status === status.HttpStatusString.CREATED) {
        message.success("Inventory added successfully!");
        form.resetFields();
        getInventories();
      }
    } catch (error) {
      message.error({ message: "Failed to add inventory." });
    }
  };

  const handleSearch = (value) => {
    setsearchTerm(value);
    getInventories();
  };

  const handlePaginationChange = (page, pageSize) => {
    setPagination({
      page,
      size: pageSize,
      total: pagination.total,
    });
  };

  const columns = [
    {
      title: "Seller",
      dataIndex: "sellerName",
      key: "sellerName",
    },
    {
      title: "Material",
      dataIndex: "materialName",
      key: "materialName",
    },
    {
      title: "Category",
      dataIndex: "categoryType",
      key: "categoryType",
    },
    {
      title: "Quantity",
      dataIndex: "quantity",
      key: "quantity",
    },
  ];

  return (
    <div style={{ display: "flex", gap: "20px" }}>
      <Card
        title="Add Inventory"
        style={{
          flex: "1",
          marginTop: "-10px",
          border: "1px solid #d9d9d9",
          boxShadow: "0 4px 8px rgba(0, 0, 0, 0.1)",
        }}
      >
        <Form
          form={form}
          layout="vertical"
          onFinish={handleAdd}
          initialValues={{
            sellerId: "",
            materialId: "",
            categoryId: "",
            buyingPrice: "",
            profitPercentage: "",
            quantity: "",
          }}
        >
          <Form.Item
            label="Seller"
            name="sellerId"
            rules={[{ required: true, message: "Please select a seller!" }]}
          >
            <Select placeholder="Select seller">
              {sellers.map((seller) => (
                <Option key={seller.sellerId} value={seller.sellerId}>
                  {seller.sellerName}
                </Option>
              ))}
            </Select>
          </Form.Item>

          <Form.Item
            label="Material"
            name="materialId"
            rules={[{ required: true, message: "Please select a material!" }]}
          >
            <Select placeholder="Select material">
              {materials.map((material) => (
                <Option key={material.materialId} value={material.materialId}>
                  {`${material.materialName}-${material.materialType}`}
                </Option>
              ))}
            </Select>
          </Form.Item>

          <Form.Item
            label="Category"
            name="categoryId"
            rules={[{ required: true, message: "Please select a category!" }]}
          >
            <Select placeholder="Select category">
              {categories.map((category) => (
                <Option key={category.categoryId} value={category.categoryId}>
                  {category.categoryType}
                </Option>
              ))}
            </Select>
          </Form.Item>

          <Form.Item
            label="Buying Price"
            name="buyingPrice"
            rules={[
              { required: true, message: "Please enter the buying price!" },
              {
                validator: (_, value) =>
                  value && value > 0
                    ? Promise.resolve()
                    : Promise.reject("Buying price must be a positive number!"),
              },
            ]}
          >
            <Input placeholder="Enter buying price" type="number" />
          </Form.Item>

          <Form.Item
            label="Profit Percentage"
            name="profitPercentage"
            rules={[
              {
                required: true,
                message: "Please enter the profit percentage!",
              },
              {
                validator: (_, value) =>
                  value && value >= 0 && value <= 100
                    ? Promise.resolve()
                    : Promise.reject(
                        "Profit percentage must be between 0 and 100!"
                      ),
              },
            ]}
          >
            <Input placeholder="Enter profit percentage" type="number" />
          </Form.Item>

          <Form.Item
            label="Quantity"
            name="quantity"
            rules={[{ required: true, message: "Please enter a quantity!" }]}
          >
            <Input placeholder="Enter quantity" type="number" />
          </Form.Item>

          <Button type="primary" htmlType="submit">
            Add Inventory
          </Button>
        </Form>
      </Card>

      <div style={{ flex: "2", marginTop: "-10px" }}>
        <h3>Inventories</h3>

        <Input.Search
          placeholder="Search Sellers,Materials,Categories..."
          allowClear
          enterButton="Search"
          size="large"
          onSearch={handleSearch}
          style={{ marginBottom: "10px" }}
        />

        <Table
          columns={columns}
          dataSource={inventories}
          rowKey="inventoryId"
          pagination={{
            current: pagination.page,
            pageSize: pagination.size,
            total: pagination.total,
            onChange: handlePaginationChange,
          }}
        />
      </div>
    </div>
  );
};

export default Inventories;
