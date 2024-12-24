package Assignment.StockManagementSystem.services.implementation;

import Assignment.StockManagementSystem.common.ErrorMessages;
import Assignment.StockManagementSystem.dto.InventoryDTOWithoutId;
import Assignment.StockManagementSystem.dto.InventoryDTOWithoutItems;
import Assignment.StockManagementSystem.models.Categories;
import Assignment.StockManagementSystem.models.Inventories;
import Assignment.StockManagementSystem.models.Materials;
import Assignment.StockManagementSystem.models.Sellers;
import Assignment.StockManagementSystem.repositories.InventoriesRepository;
import Assignment.StockManagementSystem.services.*;
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

import java.util.Optional;

@Service
public class InventoriesServiceImp implements InventoriesService {

    private static final Logger logger = LogManager.getLogger(InventoriesServiceImp.class);

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private InventoriesRepository inventoriesRepository;

    @Autowired
    private ItemsService itemsService;

    @Autowired
    private SellersService sellersService;

    @Autowired
    private CategoriesService categoriesService;

    @Autowired
    private MaterialsService materialsService;

    @Override
    public Inventories addInventory(InventoryDTOWithoutId inventoryRequestDTO) {
        try {
            Sellers seller = getSeller(inventoryRequestDTO.getSellerId());
            Materials material = getMaterial(inventoryRequestDTO.getMaterialId());
            Categories category = getCategory(inventoryRequestDTO.getCategoryId());

            Inventories inventory = findOrCreateInventory(seller, material, category, inventoryRequestDTO);

            Inventories savedInventory = saveInventory(inventory);

            addItemsToInventory(savedInventory, inventoryRequestDTO);

            return savedInventory;
        } catch (Exception ex) {
            logger.error("Error occurred while adding inventory with ID {}", ex);
            throw new InternalServerErrorException(ErrorMessages.ERR_MSG_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Page<InventoryDTOWithoutItems> getInventories(String searchTerm, Pageable pageable) {
        try {
            Page<Inventories> inventories = inventoriesRepository.getInventories(searchTerm, pageable);

            return inventories.map(this::convertToInventoryDTO);
        } catch (Exception ex) {
            logger.error("Error occurred while searching inventories", ex);
            throw new InternalServerErrorException(ErrorMessages.ERR_MSG_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Inventories updateInventory(int inventoryId, InventoryDTOWithoutId updatedInventory) {
        try {
            if (inventoryId == 0) {
                throw new BadRequestException("Inventory ID must be provided.");
            }

            Inventories existingInventory = inventoriesRepository.findById(inventoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Inventory with ID " + inventoryId + " not found."));

            modelMapper.map(updatedInventory, existingInventory);

            return inventoriesRepository.save(existingInventory);
        } catch (ResourceNotFoundException | BadRequestException ex) {
            throw ex;
        } catch (Exception ex) {
            logger.error("Error occurred while updating inventory with ID {}", inventoryId, ex);
            throw new InternalServerErrorException("An error occurred while updating the inventory. Please try again.");
        }
    }

    private Sellers getSeller(int sellerId) {
        return sellersService.getSellerById(sellerId);
    }

    private Materials getMaterial(int materialId) {
        return materialsService.getMaterialById(materialId);
    }

    private Categories getCategory(int categoryId) {
        return categoriesService.getCategoryById(categoryId);
    }

    private Inventories findOrCreateInventory(Sellers seller, Materials material, Categories category, InventoryDTOWithoutId inventoryRequestDTO) {
        Optional<Inventories> existingInventoryOptional = inventoriesRepository.findBySellerAndMaterialAndCategory(seller, material, category);

        Inventories inventory;
        if (existingInventoryOptional.isPresent()) {
            inventory = existingInventoryOptional.get();
            inventory.setQuantity(inventory.getQuantity() + inventoryRequestDTO.getQuantity());
        } else {
            inventory = new Inventories();

            inventory.setSeller(seller);
            inventory.setMaterial(material);
            inventory.setCategory(category);
            inventory.setQuantity(inventoryRequestDTO.getQuantity());

        }

        return inventory;
    }

    private Inventories saveInventory(Inventories inventory) {
        return inventoriesRepository.save(inventory);
    }

    private void addItemsToInventory(Inventories savedInventory, InventoryDTOWithoutId inventoryRequestDTO) {
        itemsService.addItems(savedInventory, inventoryRequestDTO);
    }


    private InventoryDTOWithoutItems convertToInventoryDTO(Inventories inventory) {
        InventoryDTOWithoutItems dto = new InventoryDTOWithoutItems();
        dto.setInventoryId(inventory.getInventoryId());
        dto.setSeller(inventory.getSeller());
        dto.setMaterial(inventory.getMaterial());
        dto.setCategory(inventory.getCategory());
        dto.setQuantity(inventory.getQuantity());
        return dto;
    }

}
