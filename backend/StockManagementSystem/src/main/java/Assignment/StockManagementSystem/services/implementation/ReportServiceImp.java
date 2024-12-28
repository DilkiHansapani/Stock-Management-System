package Assignment.StockManagementSystem.services.implementation;

import Assignment.StockManagementSystem.dto.SoldoutItemCountDTO;
import Assignment.StockManagementSystem.models.Items;
import Assignment.StockManagementSystem.models.Sellers;
import Assignment.StockManagementSystem.repositories.ItemsRepository;
import Assignment.StockManagementSystem.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportServiceImp implements ReportService {

    @Autowired
    ItemsRepository itemsRepository;

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
}
