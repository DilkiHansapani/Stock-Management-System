package Assignment.StockManagementSystem.dto;

import lombok.Data;

@Data
public class InventoryBulkUpdateDTO {

    private String inventoryId;

    private int bulkQuantity;

    private String status;

    private float sellingPrice;

    private float salePercentage;

    public String getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

    public int getBulkQuantity() {
        return bulkQuantity;
    }

    public void setBulkQuantity(int bulkQuantity) {
        this.bulkQuantity = bulkQuantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(float sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public float getSalePercentage() {
        return salePercentage;
    }

    public void setSalePercentage(float salePercentage) {
        this.salePercentage = salePercentage;
    }
}
