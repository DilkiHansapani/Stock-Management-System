package Assignment.StockManagementSystem.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Items {

    @Id
    @NotNull(message = "Item code cannot be null.")
    private String itemCode;

    @NotNull(message = "Date and time cannot be null.")
    private Date dateTime;

    @ManyToOne
    @JoinColumn(name = "inventoryId", nullable = false)
    @NotNull(message = "Inventory cannot be null.")
    private Inventories inventory;

    @Min(value = 0, message = "Buying price cannot be negative.")
    private float buyingPrice;

    @Min(value = 0, message = "Selling price cannot be negative.")
    private float sellingPrice;

    @Min(value = 0, message = "Profit percentage cannot be negative.")
    private float profitPercentage;

    private float salePercentage;

    @Pattern(regexp = "^(normal|sale|stockclearing|soldout)$", message = "Status must be either 'normal', 'sale', 'stockclearing', or 'soldout'.")
    private String status;

    public @NotNull(message = "Item code cannot be null.") String getItemCode() {
        return itemCode;
    }

    public void setItemCode(@NotNull(message = "Item code cannot be null.") String itemCode) {
        this.itemCode = itemCode;
    }

    public @NotNull(message = "Date and time cannot be null.") Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(@NotNull(message = "Date and time cannot be null.") Date dateTime) {
        this.dateTime = dateTime;
    }

    public @NotNull(message = "Inventory cannot be null.") Inventories getInventory() {
        return inventory;
    }

    public void setInventory(@NotNull(message = "Inventory cannot be null.") Inventories inventory) {
        this.inventory = inventory;
    }

    @Min(value = 0, message = "Buying price cannot be negative.")
    public float getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(@Min(value = 0, message = "Buying price cannot be negative.") float buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    @Min(value = 0, message = "Selling price cannot be negative.")
    public float getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(@Min(value = 0, message = "Selling price cannot be negative.") float sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    @Min(value = 0, message = "Profit percentage cannot be negative.")
    public float getProfitPercentage() {
        return profitPercentage;
    }

    public void setProfitPercentage(@Min(value = 0, message = "Profit percentage cannot be negative.") float profitPercentage) {
        this.profitPercentage = profitPercentage;
    }

    public float getSalePercentage() {
        return salePercentage;
    }

    public void setSalePercentage(float salePercentage) {
        this.salePercentage = salePercentage;
    }

    public @Pattern(regexp = "^(normal|sale|stockclearing|soldout)$", message = "Status must be either 'normal', 'sale', 'stockclearing', or 'soldout'.") String getStatus() {
        return status;
    }

    public void setStatus(@Pattern(regexp = "^(normal|sale|stockclearing|soldout)$", message = "Status must be either 'normal', 'sale', 'stockclearing', or 'soldout'.") String status) {
        this.status = status;
    }
}
