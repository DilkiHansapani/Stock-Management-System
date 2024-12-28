package Assignment.StockManagementSystem.ServiceTests;

import Assignment.StockManagementSystem.dto.SoldoutItemCountDTO;
import Assignment.StockManagementSystem.models.Items;
import Assignment.StockManagementSystem.models.Sellers;
import Assignment.StockManagementSystem.repositories.ItemsRepository;
import Assignment.StockManagementSystem.services.implementation.ReportServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ReportServiceImpTest {

    @Mock
    private ItemsRepository itemsRepository;

    @InjectMocks
    private ReportServiceImp reportServiceImp;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetSoldItemsBySeller() {
        // Arrange
        List<Object[]> mockResults = new ArrayList<>();
        Sellers seller = new Sellers();
        seller.setSellerName("Seller A");
        mockResults.add(new Object[]{seller, 10L});

        when(itemsRepository.countSoldOutItemsBySeller()).thenReturn(mockResults);

        List<SoldoutItemCountDTO> result = reportServiceImp.getSoldItemsBySeller();

        assertEquals(1, result.size());
        assertEquals("Seller A", result.get(0).getSellerName());
        assertEquals(10L, result.get(0).getCount());
        verify(itemsRepository, times(1)).countSoldOutItemsBySeller();
    }

    @Test
    void testGetSoldItemsByDateRange() {

        LocalDateTime startDate = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 12, 31, 23, 59);

        List<Items> mockItems = new ArrayList<>();
        Items item = new Items();
        item.setItemCode("Item1");
        mockItems.add(item);

        when(itemsRepository.findSoldoutItemDateRange(startDate, endDate)).thenReturn(mockItems);

        List<Items> result = reportServiceImp.getSoldItemsByDateRange(startDate, endDate);

        assertEquals(1, result.size());
        assertEquals("Item1", result.get(0).getItemCode());
        verify(itemsRepository, times(1)).findSoldoutItemDateRange(startDate, endDate);
    }

    @Test
    void testGetItemsByStatus() {
        String status = "soldout";
        List<Items> mockItems = new ArrayList<>();
        Items item = new Items();
        item.setItemCode("Item2");
        mockItems.add(item);

        when(itemsRepository.findItemsByStatus(status)).thenReturn(mockItems);

        List<Items> result = reportServiceImp.getItemsByStatus(status);

        assertEquals(1, result.size());
        assertEquals("Item2", result.get(0).getItemCode());
        verify(itemsRepository, times(1)).findItemsByStatus(status);
    }
}
