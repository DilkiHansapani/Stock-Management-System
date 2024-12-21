package Assignment.StockManagementSystem.services;

import Assignment.StockManagementSystem.dto.MaterialDTOWithoutId;
import Assignment.StockManagementSystem.dto.MaterialsDTOWithoutInventories;
import Assignment.StockManagementSystem.models.Materials;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MaterialsService {
    Materials addMaterial(MaterialDTOWithoutId material);

    public Page<MaterialsDTOWithoutInventories> getMaterials(String materialName, String materialType, Pageable pageable);

    Materials getMaterialById(int materialId);

    Materials updateMaterial(int materialId, MaterialDTOWithoutId updatedMaterial);
}
