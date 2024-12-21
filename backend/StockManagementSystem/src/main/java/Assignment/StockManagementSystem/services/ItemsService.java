package Assignment.StockManagementSystem.services;

import Assignment.StockManagementSystem.dto.InventoryDTOWithoutId;
import Assignment.StockManagementSystem.dto.ItemsDTOWithoutInventories;
import Assignment.StockManagementSystem.models.Inventories;
import Assignment.StockManagementSystem.models.Items;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public interface ItemsService {

    public void addItems(Inventories inventory, InventoryDTOWithoutId inventoryRequestDTO);

    public Page<ItemsDTOWithoutInventories> getItems(String itemCode, Date startDateTime, Date endDateTime, String status, Pageable pageable);

    public Items updateItem(String itemCode, Items updatedItem);

}
