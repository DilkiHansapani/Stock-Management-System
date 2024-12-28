package Assignment.StockManagementSystem.services;

import Assignment.StockManagementSystem.dto.SoldoutItemCountDTO;
import Assignment.StockManagementSystem.models.Items;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface ReportService {
    public List<SoldoutItemCountDTO> getSoldItemsBySeller();

    public List<Items> getSoldItemsByDateRange(LocalDateTime startDateTimeParsed, LocalDateTime endDateTimeParsed);

    public List<Items> getItemsByStatus(String status);
}
