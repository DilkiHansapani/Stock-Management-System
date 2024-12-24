package Assignment.StockManagementSystem.services;

import Assignment.StockManagementSystem.dto.MaterialDTOWithoutId;
import Assignment.StockManagementSystem.dto.MaterialsDTOWithoutInventories;
import Assignment.StockManagementSystem.models.Materials;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MaterialsService {
    public Materials addMaterial(MaterialDTOWithoutId material);

    public Page<MaterialsDTOWithoutInventories> getMaterials(String searchTerm, Pageable pageable);

    public Materials getMaterialById(int materialId);

    public List<Materials> getAllMaterials();

    public Materials updateMaterial(int materialId, MaterialDTOWithoutId updatedMaterial);
}
