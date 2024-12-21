package Assignment.StockManagementSystem.dto;

import Assignment.StockManagementSystem.models.Inventories;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CategoriesDTOWithoutInventories {

    private int categoryId;

    private String categoryType;

    private List<Inventories> inventories;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public List<Inventories> getInventories() {
        return inventories;
    }

    public void setInventories(List<Inventories> inventories) {
        this.inventories = inventories;
    }
}
