package Assignment.StockManagementSystem.dto;

import lombok.Data;

@Data
public class InventoryDTOWithoutId {


    private int sellerId;
    private int materialId;
    private int categoryId;
    private int quantity;
    private float buyingPrice;
    private float profitPercentage;
    private float salePercentage = 0.0f;
    private float stockClearingPrice = 0.0f;
    private String status = "normal";

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(float buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public float getProfitPercentage() {
        return profitPercentage;
    }

    public void setProfitPercentage(float profitPercentage) {
        this.profitPercentage = profitPercentage;
    }

    public float getSalePercentage() {
        return salePercentage;
    }

    public void setSalePercentage(float salePercentage) {
        this.salePercentage = salePercentage;
    }

    public float getStockClearingPrice() {
        return stockClearingPrice;
    }

    public void setStockClearingPrice(float stockClearingPrice) {
        this.stockClearingPrice = stockClearingPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
