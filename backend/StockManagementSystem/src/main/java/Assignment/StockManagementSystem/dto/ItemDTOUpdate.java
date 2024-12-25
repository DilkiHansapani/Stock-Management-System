package Assignment.StockManagementSystem.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ItemDTOUpdate {
    private String itemCode;

    private float sellingPrice;

    private float salePercentage;

    private String status;

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
