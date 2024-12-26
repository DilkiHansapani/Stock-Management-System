import React, { useState } from "react";
import {
  HomeOutlined,
  UserOutlined,
  ShopOutlined,
  UnorderedListOutlined,
  ShoppingOutlined,
  BarChartOutlined,
} from "@ant-design/icons";
import "../../Styles/Sidebar.css";

const Sidebar = ({ onMenuSelect }) => {
  const [activeItem, setActiveItem] = useState("Sellers");

  const menuItems = [
    { key: "Sellers", icon: <UserOutlined />, label: "Sellers" },
    { key: "Products", icon: <ShopOutlined />, label: "Product" },
    {
      key: "Inventories",
      icon: <UnorderedListOutlined />,
      label: "Inventories",
    },
    { key: "Items", icon: <ShoppingOutlined />, label: "Items" },
    { key: "Orders", icon: <HomeOutlined />, label: "Orders" },
  ];

  const handleMenuClick = (key) => {
    setActiveItem(key);
    onMenuSelect(key);
  };

  return (
    <div className="sidebar">
      {menuItems.map((item) => (
        <div
          key={item.key}
          className={`sidebar-item ${activeItem === item.key ? "active" : ""}`}
          onClick={() => handleMenuClick(item.key)}
        >
          {item.icon}
          <span>{item.label}</span>
        </div>
      ))}
    </div>
  );
};

export default Sidebar;
