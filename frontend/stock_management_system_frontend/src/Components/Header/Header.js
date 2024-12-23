import React from "react";
import { Button } from "antd";
import "../../Styles/Header.css";

const Header = () => {
  return (
    <div className="header">
      <div className="logo">Inventory Management</div>
      <div className="actions">
        <Button type="link">Profile</Button>
        <Button type="primary">Log Out</Button>
      </div>
    </div>
  );
};

export default Header;
