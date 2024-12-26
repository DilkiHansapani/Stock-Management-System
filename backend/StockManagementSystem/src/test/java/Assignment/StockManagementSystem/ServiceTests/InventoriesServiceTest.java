package Assignment.StockManagementSystem.ServiceTests;

import Assignment.StockManagementSystem.common.ErrorMessages;
import Assignment.StockManagementSystem.dto.InventoryBulkUpdateDTO;
import Assignment.StockManagementSystem.dto.InventoryDTOWithoutId;
import Assignment.StockManagementSystem.dto.InventoryDTOWithoutItems;
import Assignment.StockManagementSystem.exceptions.BadRequestException;
import Assignment.StockManagementSystem.exceptions.InternalServerErrorException;
import Assignment.StockManagementSystem.exceptions.ResourceNotFoundException;
import Assignment.StockManagementSystem.models.*;
import Assignment.StockManagementSystem.repositories.InventoriesRepository;
import Assignment.StockManagementSystem.repositories.ItemsRepository;
import Assignment.StockManagementSystem.services.CategoriesService;
import Assignment.StockManagementSystem.services.ItemsService;
import Assignment.StockManagementSystem.services.MaterialsService;
import Assignment.StockManagementSystem.services.SellersService;
import Assignment.StockManagementSystem.services.implementation.InventoriesServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventoriesServiceImpTest {

    @InjectMocks
    private InventoriesServiceImp inventoriesService;

    @Mock
    private InventoriesRepository inventoriesRepository;

    @Mock
    private ItemsRepository itemsRepository;

    @Mock
    private ItemsService itemsService;

    @Mock
    private SellersService sellersService;

    @Mock
    private CategoriesService categoriesService;

    @Mock
    private MaterialsService materialsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddInventorySuccess() {
        InventoryDTOWithoutId requestDTO = new InventoryDTOWithoutId();
        requestDTO.setSellerId(1);
        requestDTO.setMaterialId(1);
        requestDTO.setCategoryId(1);
        requestDTO.setQuantity(10);

        Sellers seller = new Sellers();
        Materials material = new Materials();
        Categories category = new Categories();
        Inventories inventory = new Inventories();
        inventory.setSeller(seller);
        inventory.setMaterial(material);
        inventory.setCategory(category);
        inventory.setQuantity(10);

        when(sellersService.getSellerById(1)).thenReturn(seller);
        when(materialsService.getMaterialById(1)).thenReturn(material);
        when(categoriesService.getCategoryById(1)).thenReturn(category);
        when(inventoriesRepository.findBySellerAndMaterialAndCategory(seller, material, category)).thenReturn(Optional.empty());
        when(inventoriesRepository.save(any(Inventories.class))).thenReturn(inventory);

        Inventories result = inventoriesService.addInventory(requestDTO);

        assertNotNull(result);
        assertEquals(10, result.getQuantity());
        verify(inventoriesRepository, times(1)).save(any(Inventories.class));
    }

    @Test
    void testAddInventoryInternalServerError() {
        InventoryDTOWithoutId requestDTO = new InventoryDTOWithoutId();
        requestDTO.setSellerId(1);

        when(sellersService.getSellerById(1)).thenThrow(new RuntimeException("Database error"));

        InternalServerErrorException exception = assertThrows(
                InternalServerErrorException.class,
                () -> inventoriesService.addInventory(requestDTO)
        );

        assertEquals(ErrorMessages.ERR_MSG_INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @Test
    void testGetInventoriesSuccess() {
        Page<Inventories> inventoriesPage = new PageImpl<>(Collections.emptyList());
        when(inventoriesRepository.getInventories(anyString(), any(Pageable.class))).thenReturn(inventoriesPage);

        Page<InventoryDTOWithoutItems> result = inventoriesService.getInventories("", Pageable.unpaged());

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetInventoriesInternalServerError() {
        when(inventoriesRepository.getInventories(anyString(), any(Pageable.class)))
                .thenThrow(new RuntimeException("Database error"));

        InternalServerErrorException exception = assertThrows(
                InternalServerErrorException.class,
                () -> inventoriesService.getInventories("", Pageable.unpaged())
        );

        assertEquals(ErrorMessages.ERR_MSG_INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @Test
    void testUpdateInventorySuccess() {
        InventoryBulkUpdateDTO updateDTO = new InventoryBulkUpdateDTO();
        updateDTO.setStatus("stockClearing");
        updateDTO.setSellingPrice(50.0f);

        Inventories existingInventory = new Inventories();
        existingInventory.setInventoryId(1);

        List<Items> items = new ArrayList<>();
        when(inventoriesRepository.findById(1)).thenReturn(Optional.of(existingInventory));
        when(itemsService.getItemsByInventory(existingInventory, 0)).thenReturn(items);

        Inventories result = inventoriesService.updateInventory(1, updateDTO);

        assertNotNull(result);
        verify(itemsRepository, times(1)).saveAll(items);
    }

    @Test
    void testUpdateInventoryNotFound() {
        when(inventoriesRepository.findById(1)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> inventoriesService.updateInventory(1, new InventoryBulkUpdateDTO())
        );

        assertEquals("Inventory with ID 1 not found.", exception.getMessage());
    }

    @Test
    void testUpdateInventoryBadRequest() {
        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> inventoriesService.updateInventory(0, new InventoryBulkUpdateDTO())
        );

        assertEquals("Inventory ID must be provided.", exception.getMessage());
    }
}
