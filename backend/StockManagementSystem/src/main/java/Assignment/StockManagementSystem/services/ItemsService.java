package Assignment.StockManagementSystem.services;

import Assignment.StockManagementSystem.dto.InventoryDTOWithoutId;
import Assignment.StockManagementSystem.dto.ItemDTOUpdate;
import Assignment.StockManagementSystem.dto.ItemsDTOWithoutInventories;
import Assignment.StockManagementSystem.models.Inventories;
import Assignment.StockManagementSystem.models.Items;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public interface ItemsService {

    public void addItems(Inventories inventory, InventoryDTOWithoutId inventoryRequestDTO);

    public Page<ItemsDTOWithoutInventories> getItems(String itemCode, LocalDateTime startDateTime, LocalDateTime endDateTime, String status, Pageable pageable);

    public Items updateItem(String itemCode, ItemDTOUpdate updatedItem);

    List<Items> getItemsByInventory(Inventories existingInventory, int bulkQuantity);

}
