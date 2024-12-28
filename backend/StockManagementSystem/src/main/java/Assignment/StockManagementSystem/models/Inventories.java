package Assignment.StockManagementSystem.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Data
public class Inventories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int inventoryId;

    @ManyToOne
    @JoinColumn(name="sellerId", nullable = false)
    @NotNull(message = "Seller cannot be null.")
    private Sellers seller;

    @ManyToOne
    @JoinColumn(name = "materialId", nullable = false)
    @NotNull(message = "Material cannot be null.")
    private Materials material;

    @ManyToOne
    @JoinColumn(name = "categoryId", nullable = false)
    @NotNull(message = "Category cannot be null.")
    private Categories category;

    @Min(value = 0, message = "Quantity cannot be negative.")
    private int quantity;

    @OneToMany(mappedBy = "inventory")
    @JsonIgnore
    private List<Items> items;

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public @NotNull(message = "Seller cannot be null.") Sellers getSeller() {
        return seller;
    }

    public void setSeller(@NotNull(message = "Seller cannot be null.") Sellers seller) {
        this.seller = seller;
    }

    public @NotNull(message = "Material cannot be null.") Materials getMaterial() {
        return material;
    }

    public void setMaterial(@NotNull(message = "Material cannot be null.") Materials material) {
        this.material = material;
    }

    public @NotNull(message = "Category cannot be null.") Categories getCategory() {
        return category;
    }

    public void setCategory(@NotNull(message = "Category cannot be null.") Categories category) {
        this.category = category;
    }

    @Min(value = 0, message = "Quantity cannot be negative.")
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(@Min(value = 0, message = "Quantity cannot be negative.") int quantity) {
        this.quantity = quantity;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }
}
