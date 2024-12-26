package Assignment.StockManagementSystem.ServiceTests;

import Assignment.StockManagementSystem.dto.ItemsDTOWithoutInventories;
import Assignment.StockManagementSystem.models.Inventories;
import Assignment.StockManagementSystem.models.Items;
import Assignment.StockManagementSystem.models.Materials;
import Assignment.StockManagementSystem.models.Sellers;
import Assignment.StockManagementSystem.repositories.ItemsRepository;
import Assignment.StockManagementSystem.repositories.InventoriesRepository;
import Assignment.StockManagementSystem.dto.InventoryDTOWithoutId;
import Assignment.StockManagementSystem.dto.ItemDTOUpdate;
import Assignment.StockManagementSystem.dto.SoldoutItemCountDTO;
import Assignment.StockManagementSystem.exceptions.ResourceNotFoundException;
import Assignment.StockManagementSystem.services.implementation.ItemsServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemsServiceImpTest {

    @InjectMocks
    private ItemsServiceImp itemsService;

    @Mock
    private ItemsRepository itemsRepository;

    @Mock
    private InventoriesRepository inventoriesRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testAddItems() {
        Inventories inventory = mock(Inventories.class);
        Sellers seller = new Sellers();
        seller.setSellerName("Test Seller");

        Materials material = new Materials();
        material.setMaterialName("Material Name");
        material.setMaterialType("Material Type");

        when(inventory.getSeller()).thenReturn(seller);
        when(inventory.getMaterial()).thenReturn(material);

        InventoryDTOWithoutId inventoryDTO = new InventoryDTOWithoutId();
        inventoryDTO.setQuantity(2);
        inventoryDTO.setBuyingPrice(100);
        inventoryDTO.setProfitPercentage(20);
        inventoryDTO.setSalePercentage(10);
        inventoryDTO.setStatus("normal");

        itemsService.addItems(inventory, inventoryDTO);

        verify(itemsRepository, times(2)).save(any(Items.class));
    }


    @Test
    void testGetItems() {
        List<Items> itemsList = new ArrayList<>();
        itemsList.add(new Items());
        Page<Items> itemsPage = new PageImpl<>(itemsList);

        when(itemsRepository.findItems(anyString(), any(), any(), anyString(), any(Pageable.class)))
                .thenReturn(itemsPage);

        Page<ItemsDTOWithoutInventories> result = itemsService.getItems("itemCode", LocalDateTime.now(), LocalDateTime.now(), "status", PageRequest.of(0, 10));

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void testUpdateItem_Success() {
        Items existingItem = new Items();
        existingItem.setBuyingPrice(100);
        existingItem.setProfitPercentage(20);

        ItemDTOUpdate updateDTO = new ItemDTOUpdate();
        updateDTO.setSalePercentage(10);
        updateDTO.setStatus("sale");

        when(itemsRepository.findById("itemCode")).thenReturn(Optional.of(existingItem));
        when(itemsRepository.save(any(Items.class))).thenReturn(existingItem);

        Items result = itemsService.updateItem("itemCode", updateDTO);

        assertNotNull(result);
        assertEquals("sale", result.getStatus());
    }

    @Test
    void testUpdateItem_ItemNotFound() {
        when(itemsRepository.findById("itemCode")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> itemsService.updateItem("itemCode", new ItemDTOUpdate()));
    }

    @Test
    void testGetSoldItemsBySeller() {
        List<Object[]> mockResults = new ArrayList<>();
        Sellers seller = new Sellers();
        seller.setSellerName("Seller1");
        mockResults.add(new Object[]{seller, 10L});

        when(itemsRepository.countSoldOutItemsBySeller()).thenReturn(mockResults);

        List<SoldoutItemCountDTO> result = itemsService.getSoldItemsBySeller();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Seller1", result.get(0).getSellerName());
        assertEquals(10, result.get(0).getCount());
    }

    @Test
    void testGetSoldItemsByDateRange() {
        List<Items> mockItems = new ArrayList<>();
        mockItems.add(new Items());

        when(itemsRepository.findSoldoutItemDateRange(any(), any())).thenReturn(mockItems);

        List<Items> result = itemsService.getSoldItemsByDateRange(LocalDateTime.now(), LocalDateTime.now());

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testGetItemsByStatus() {
        List<Items> mockItems = new ArrayList<>();
        mockItems.add(new Items());

        when(itemsRepository.findItemsByStatus("normal")).thenReturn(mockItems);

        List<Items> result = itemsService.getItemsByStatus("normal");

        assertNotNull(result);
        assertEquals(1, result.size());
    }
}