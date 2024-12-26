package Assignment.StockManagementSystem.services.implementation;

import Assignment.StockManagementSystem.common.ErrorMessages;
import Assignment.StockManagementSystem.dto.InventoryDTOWithoutId;
import Assignment.StockManagementSystem.dto.ItemDTOUpdate;
import Assignment.StockManagementSystem.dto.ItemsDTOWithoutInventories;
import Assignment.StockManagementSystem.dto.SoldoutItemCountDTO;
import Assignment.StockManagementSystem.models.Inventories;
import Assignment.StockManagementSystem.models.Items;
import Assignment.StockManagementSystem.models.Sellers;
import Assignment.StockManagementSystem.repositories.InventoriesRepository;
import Assignment.StockManagementSystem.repositories.ItemsRepository;
import Assignment.StockManagementSystem.services.ItemsService;
import Assignment.StockManagementSystem.utils.ItemsUtil;
import Assignment.StockManagementSystem.exceptions.BadRequestException;
import Assignment.StockManagementSystem.exceptions.InternalServerErrorException;
import Assignment.StockManagementSystem.exceptions.ResourceNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ItemsServiceImp implements ItemsService {

    private static final Logger logger = LogManager.getLogger(ItemsServiceImp.class);

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private ItemsRepository itemsRepository;

    @Autowired
    private InventoriesRepository inventoriesRepository;

    @Override
    public void addItems(Inventories inventory, InventoryDTOWithoutId inventoryRequestDTO) {
        int existingItemCount = inventory.getItems() != null ? inventory.getItems().size() : 0;

        for (int i = 1; i <= inventoryRequestDTO.getQuantity(); i++) {
            Items item = new Items();

            String itemCode = ItemsUtil.generateItemCode(
                    inventory.getSeller().getSellerName(),
                    inventory.getMaterial().getMaterialName(),
                    inventory.getMaterial().getMaterialType(),
                    existingItemCount + i
            );

            item.setItemCode(itemCode);
            item.setDateTime(LocalDateTime.now());
            item.setInventory(inventory);
            item.setBuyingPrice(inventoryRequestDTO.getBuyingPrice());
            item.setProfitPercentage(inventoryRequestDTO.getProfitPercentage());
            item.setSalePercentage(inventoryRequestDTO.getSalePercentage());
            item.setStatus(inventoryRequestDTO.getStatus());

            float sellingPrice = ItemsUtil.calculateSellingPrice(
                    inventoryRequestDTO.getBuyingPrice(),
                    inventoryRequestDTO.getProfitPercentage(),
                    inventoryRequestDTO.getSalePercentage(),
                    inventoryRequestDTO.getStockClearingPrice(),
                    inventoryRequestDTO.getStatus()
            );

            item.setSellingPrice(sellingPrice);

            itemsRepository.save(item);
        }
    }

    @Override
    public Page<ItemsDTOWithoutInventories> getItems(String itemCode, LocalDateTime startDateTime, LocalDateTime endDateTime, String status, Pageable pageable) {
        try {
            Page<Items> items = itemsRepository.findItems(itemCode, startDateTime, endDateTime, status, pageable);
            return items.map(this::convertToItemsDTO);
        } catch (Exception ex) {
            logger.error("Error occurred retrieving items", ex);
            throw new InternalServerErrorException("An error occurred while fetching the items.");
        }
    }

    @Override
    public Items updateItem(String itemCode, ItemDTOUpdate updatedItem) {
        try {
            if (itemCode == null || itemCode.isEmpty()) {
                throw new BadRequestException("Item code must be provided.");
            }

            Items existingItem = itemsRepository.findById(itemCode)
                    .orElseThrow(() -> new ResourceNotFoundException("Item with code " + itemCode + " not found."));


            existingItem.setStatus(updatedItem.getStatus());
            existingItem.setSalePercentage(updatedItem.getSalePercentage());


            float sellingPrice = ItemsUtil.calculateSellingPrice(
                    existingItem.getBuyingPrice(),
                    existingItem.getProfitPercentage(),
                    updatedItem.getSalePercentage(),
                    updatedItem.getSellingPrice(),
                    existingItem.getStatus()
            );

            existingItem.setSellingPrice(sellingPrice);

            return itemsRepository.save(existingItem);
        } catch (ResourceNotFoundException | BadRequestException ex) {
            throw ex;
        } catch (Exception ex) {
            logger.error("Error occurred while updating item with code " + itemCode, ex);
            throw new InternalServerErrorException(ErrorMessages.ERR_MSG_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<SoldoutItemCountDTO> getSoldItemsBySeller() {

        List<Object[]> results = itemsRepository.countSoldOutItemsBySeller();

        List<SoldoutItemCountDTO> soldoutItemCountDTOList = new ArrayList<>();

        for (Object[] result : results) {
            Sellers seller = (Sellers) result[0];
            Long count = (Long) result[1];

            SoldoutItemCountDTO dto = new SoldoutItemCountDTO();
            dto.setSellerName(seller.getSellerName());
            dto.setCount(count);

            soldoutItemCountDTOList.add(dto);
        }

        return soldoutItemCountDTOList;
    }


    @Override
    public List<Items> getSoldItemsByDateRange(LocalDateTime startDateTimeParsed, LocalDateTime endDateTimeParsed) {
        List<Items> items =  itemsRepository.findSoldoutItemDateRange(startDateTimeParsed,endDateTimeParsed);
        return items;
    }

    @Override
    public List<Items> getItemsByStatus(String status) {
        List<Items> items = itemsRepository.findItemsByStatus(status);
        return items;
    }

    private ItemsDTOWithoutInventories convertToItemsDTO(Items item) {
        ItemsDTOWithoutInventories dto = new ItemsDTOWithoutInventories();
        dto.setItemCode(item.getItemCode());
        dto.setDateTime(item.getDateTime());
        dto.setBuyingPrice(item.getBuyingPrice());
        dto.setSellingPrice(item.getSellingPrice());
        dto.setProfitPercentage(item.getProfitPercentage());
        dto.setSalePercentage(item.getSalePercentage());
        dto.setStatus(item.getStatus());

        if (item.getInventory() != null && item.getInventory().getCategory() != null) {
            dto.setCategoryType(item.getInventory().getCategory().getCategoryType());
        }

        return dto;
    }

    public List<Items> getItemsByInventory(Inventories inventory, int bulkQuantity) {
        return itemsRepository.findFirstNByInventoryOrderByDateTimeAsc(inventory, bulkQuantity);
    }

}
