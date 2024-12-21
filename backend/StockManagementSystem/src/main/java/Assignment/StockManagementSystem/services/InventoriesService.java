package Assignment.StockManagementSystem.services;

import Assignment.StockManagementSystem.dto.InventoryDTOWithoutId;
import Assignment.StockManagementSystem.dto.InventoryDTOWithoutItems;
import Assignment.StockManagementSystem.models.Inventories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface InventoriesService {

    Inventories addInventory(InventoryDTOWithoutId inventoryRequestDTO);

    Page<InventoryDTOWithoutItems> getInventories(String materialName, String sellerName, String categoryType, Pageable pageable);

    Inventories updateInventory(int inventoryId, InventoryDTOWithoutId updatedInventory);

}