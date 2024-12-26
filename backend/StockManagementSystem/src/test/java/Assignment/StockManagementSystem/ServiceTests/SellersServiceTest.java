package Assignment.StockManagementSystem.ServiceTests;

import Assignment.StockManagementSystem.common.ErrorMessages;
import Assignment.StockManagementSystem.dto.SellerDTOWitohutId;
import Assignment.StockManagementSystem.dto.SellerDTOWithoutInventories;
import Assignment.StockManagementSystem.exceptions.DuplicateResourceException;
import Assignment.StockManagementSystem.exceptions.InternalServerErrorException;
import Assignment.StockManagementSystem.exceptions.ResourceNotFoundException;
import Assignment.StockManagementSystem.models.Sellers;
import Assignment.StockManagementSystem.repositories.SellersRepository;
import Assignment.StockManagementSystem.services.implementation.SellersServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SellersServiceImpTest {

    @InjectMocks
    private SellersServiceImp sellersService;

    @Mock
    private SellersRepository sellersRepository;

    private Sellers seller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        seller = new Sellers();
        seller.setSellerId(1);
        seller.setSellerName("Test Seller");
        seller.setEmail("test@example.com");
        seller.setContact("1234567890");
        seller.setAddress("Test Address");
        seller.setStatus("Active");
    }

    @Test
    void testAddSellerSuccess() {
        SellerDTOWitohutId sellerDTO = new SellerDTOWitohutId();
        sellerDTO.setSellerName("Test Seller");
        sellerDTO.setEmail("test@example.com");
        sellerDTO.setContact("1234567890");
        sellerDTO.setAddress("Test Address");

        when(sellersRepository.findByEmail(sellerDTO.getEmail())).thenReturn(Optional.empty());
        when(sellersRepository.save(any(Sellers.class))).thenReturn(seller);

        Sellers result = sellersService.addSeller(sellerDTO);

        assertNotNull(result);
        assertEquals(seller.getSellerName(), result.getSellerName());
        verify(sellersRepository, times(1)).save(any(Sellers.class));
    }

    @Test
    void testAddSellerDuplicateEmail() {
        SellerDTOWitohutId sellerDTO = new SellerDTOWitohutId();
        sellerDTO.setEmail("test@example.com");

        when(sellersRepository.findByEmail(sellerDTO.getEmail())).thenReturn(Optional.of(seller));

        assertThrows(DuplicateResourceException.class, () -> sellersService.addSeller(sellerDTO));
        verify(sellersRepository, never()).save(any(Sellers.class));
    }

    @Test
    void testGetSellers() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Sellers> page = new PageImpl<>(Collections.singletonList(seller));

        when(sellersRepository.findSellers(null, pageable)).thenReturn(page);

        Page<SellerDTOWithoutInventories> result = sellersService.getSellers(null, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(sellersRepository, times(1)).findSellers(null, pageable);
    }

    @Test
    void testGetSellerByIdSuccess() {
        when(sellersRepository.findById(1)).thenReturn(Optional.of(seller));

        Sellers result = sellersService.getSellerById(1);

        assertNotNull(result);
        assertEquals(seller.getSellerName(), result.getSellerName());
        verify(sellersRepository, times(1)).findById(1);
    }

    @Test
    void testGetSellerByIdNotFound() {
        when(sellersRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> sellersService.getSellerById(1));
        verify(sellersRepository, times(1)).findById(1);
    }

    @Test
    void testUpdateSellerSuccess() {
        SellerDTOWitohutId updatedSeller = new SellerDTOWitohutId();
        updatedSeller.setSellerName("Updated Seller");
        updatedSeller.setEmail("updated@example.com");

        when(sellersRepository.findById(1)).thenReturn(Optional.of(seller));
        when(sellersRepository.findByEmail(updatedSeller.getEmail())).thenReturn(Optional.empty());
        when(sellersRepository.save(any(Sellers.class))).thenReturn(seller);

        Sellers result = sellersService.updateSeller(1, updatedSeller);

        assertNotNull(result);
        assertEquals(updatedSeller.getSellerName(), result.getSellerName());
        verify(sellersRepository, times(1)).save(any(Sellers.class));
    }
}
