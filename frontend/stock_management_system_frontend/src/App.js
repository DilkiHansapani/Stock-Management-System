import React, { useState } from "react";
import Header from "./Components/Header/Header";
import Sidebar from "./Components/SideMenu/Sidebar";
import Footer from "./Components/Footer/Footer";

import Sellers from "./Components/PageContent/Sellers";
import Products from "./Components/PageContent/Products";
import Inventories from "./Components/PageContent/Inventories";
import Items from "./Components/PageContent/Items";
import Orders from "./Components/PageContent/Orders";
import Reports from "./Components/PageContent/Reports";

function App() {
  const [selectedContent, setSelectedContent] = useState("Manage Sellers");

  const renderContent = () => {
    switch (selectedContent) {
      case "Sellers":
        return <Sellers />;
      case "Products":
        return <Products />;
      case "Inventories":
        return <Inventories />;
      case "Items":
        return <Items />;
      case "Orders":
        return <Orders />;
      case "Reports":
        return <Reports />;
      default:
        return <h1>Welcome to Inventory Management</h1>;
    }
  };

  return (
    <div style={{ display: "flex", flexDirection: "column", height: "100vh" }}>
      <Header />
      <div style={{ display: "flex", flex: 1 }}>
        <Sidebar onMenuSelect={(item) => setSelectedContent(item)} />
        <div style={{ flex: 1, padding: "20px", overflowY: "auto" }}>
          {renderContent()}
        </div>
      </div>
      <Footer />
    </div>
  );
}

export default App;
