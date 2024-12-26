import React, { useState, useEffect } from "react";
import { Input, Button, Modal, Tag } from "antd";
import { PlusOutlined, EditOutlined } from "@ant-design/icons";
import TableComponent from "../Common/TableComponent";
import { fetchSellers } from "../../Services/SellerService";
import SellerForm from "../SellersComponents/SellerForm";

const Sellers = () => {
  const [searchTerm, setSearchTerm] = useState("");
  const [sellers, setSellers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [filters, setFilters] = useState("");
  const [pagination, setPagination] = useState({ page: 1, size: 10, total: 0 });
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isSellerAdded, setIsSellerAdded] = useState(false);
  const [editingSeller, setEditingSeller] = useState(null);

  const getSellers = async () => {
    try {
      setLoading(true);
      const response = await fetchSellers({
        filters,
        pagination: {
          ...pagination,
          page: pagination.page - 1, // Convert AntD page (1-based) to backend page (0-based)
        },
      });

      const { content, pageable } = response.data;
      const transformedData = content.map((seller) => ({
        ...seller,
        key: seller.sellerId,
      }));

      setSellers(transformedData);
      setPagination({
        page: pageable.pageNumber + 1, // Convert backend page (0-based) to AntD page (1-based)
        size: pageable.pageSize,
        total: response.data.totalElements,
      });
      setIsSellerAdded(false);
    } catch (err) {
      setError("Failed to fetch sellers.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    getSellers();
    setIsSellerAdded(false);
  }, [filters, pagination.page, isSellerAdded]);

  const columns = [
    {
      title: "Seller Name",
      dataIndex: "sellerName",
      key: "sellerName",
    },
    {
      title: "Email",
      dataIndex: "email",
      key: "email",
    },
    {
      title: "Contact details",
      dataIndex: "contact",
      key: "contact",
    },
    {
      title: "Address",
      dataIndex: "address",
      key: "address",
    },
    {
      title: "Status",
      dataIndex: "status",
      key: "status",
      render: (status) => (
        <Tag color={status === "Active" ? "blue" : "red"}>{status}</Tag>
      ),
    },
    {
      title: "Actions",
      key: "actions",
      render: (text, seller) => (
        <Button icon={<EditOutlined />} onClick={() => handleEdit(seller)}>
          Edit
        </Button>
      ),
    },
  ];

  const handleSearch = () => {
    setFilters(searchTerm);
  };

  const handleClear = () => {
    setSearchTerm("");
    setFilters("");
  };

  const handlePaginationChange = (page, pageSize) => {
    setPagination({
      page,
      size: pageSize,
      total: pagination.total,
    });
  };

  const handleEdit = (seller) => {
    setEditingSeller(seller);
    setIsModalOpen(true);
  };

  return (
    <div>
      <h2 style={{ marginTop: "15px" }}>Manage Sellers</h2>
      <div
        style={{
          display: "flex",
          alignItems: "center",
          marginBottom: "20px",
        }}
      >
        <Input
          placeholder="Search Sellers, Emails..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          allowClear={true}
          onClear={handleClear}
          style={{ width: "300px" }}
        />
        <Button
          type="primary"
          onClick={handleSearch}
          style={{ marginRight: "650px", marginLeft: "10px" }}
        >
          Search
        </Button>

        <Button
          type="primary"
          icon={<PlusOutlined />}
          onClick={() => setIsModalOpen(true)}
        >
          Add Seller
        </Button>
      </div>

      <TableComponent
        columns={columns}
        data={sellers}
        loading={loading}
        pagination={pagination}
        onPaginationChange={handlePaginationChange}
      />

      <Modal
        title={editingSeller ? "Edit Seller" : "Add New Seller"}
        open={isModalOpen}
        onCancel={() => {
          setIsModalOpen(false);
          setEditingSeller(null);
        }}
        footer={null}
      >
        <SellerForm
          editingSeller={editingSeller}
          onUpdateSeller={() => {
            setIsModalOpen(false);
            setEditingSeller(null);
            setIsSellerAdded(true);
          }}
          onFormSubmit={() => {
            setIsModalOpen(false);
            setIsSellerAdded(true);
          }}
          onClose={() => {
            setIsModalOpen(false);
            setEditingSeller(null);
          }}
        />
      </Modal>
    </div>
  );
};

export default Sellers;
