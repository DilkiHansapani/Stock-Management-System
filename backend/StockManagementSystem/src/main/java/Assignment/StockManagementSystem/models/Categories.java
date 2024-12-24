package Assignment.StockManagementSystem.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Categories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;

    @NotEmpty(message = "Category type is required.")
    @Size(min = 1, max = 100, message = "Category type must be between 3 and 100 characters.")
    private String categoryType;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Inventories> inventories;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public @NotEmpty(message = "Category type is required.") @Size(min = 1, max = 100, message = "Category type must be between 3 and 100 characters.") String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(@NotEmpty(message = "Category type is required.") @Size(min = 1, max = 100, message = "Category type must be between 3 and 100 characters.") String categoryType) {
        this.categoryType = categoryType;
    }

    public List<Inventories> getInventories() {
        return inventories;
    }

    public void setInventories(List<Inventories> inventories) {
        this.inventories = inventories;
    }
}
