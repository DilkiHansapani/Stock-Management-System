import React from "react";
import { Button } from "antd";
import logo from "../../Styles/Images/logo.jpg";
import "../../Styles/Header.css";

const Header = () => {
  return (
    <div className="header">
      <div className="logo">
        <img
          src={logo}
          alt="Inventory Management Logo"
          className="logo-image"
        />
      </div>
      <div className="actions">
        <Button type="link">Profile</Button>
        <Button type="primary">Log Out</Button>
      </div>
    </div>
  );
};

export default Header;
