package Assignment.StockManagementSystem.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Materials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int materialId;

    @NotNull(message = "Material name cannot be null.")
    @Size(min = 1, max = 100, message = "Material name must be between 1 and 100 characters.")
    private String materialName;

    @NotNull(message = "Material type cannot be null.")
    @Size(min = 1, max = 50, message = "Material type must be between 1 and 50 characters.")
    private String materialType;

    @OneToMany(mappedBy = "material")
    @JsonIgnore
    private List<Inventories> inventories;

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public List<Inventories> getInventories() {
        return inventories;
    }

    public void setInventories(List<Inventories> inventories) {
        this.inventories = inventories;
    }
}
