package Assignment.StockManagementSystem.services;

import Assignment.StockManagementSystem.dto.SellerDTOWithoutInventories;
import Assignment.StockManagementSystem.dto.SellerDTOWitohutId;
import Assignment.StockManagementSystem.models.Sellers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface SellersService {

    public Sellers addSeller(SellerDTOWitohutId seller);

    public Page<SellerDTOWithoutInventories> getSellers(String searchTerm, Pageable pageable);

    public Sellers getSellerById(int sellerId);

    public Sellers updateSeller(int sellerId, SellerDTOWitohutId updatedSeller);


}
