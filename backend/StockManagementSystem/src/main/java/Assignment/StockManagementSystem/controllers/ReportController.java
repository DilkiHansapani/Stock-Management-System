package Assignment.StockManagementSystem.controllers;

import Assignment.StockManagementSystem.dto.SoldoutItemCountDTO;
import Assignment.StockManagementSystem.exceptions.BadRequestException;
import Assignment.StockManagementSystem.exceptions.InternalServerErrorException;
import Assignment.StockManagementSystem.exceptions.ResourceNotFoundException;
import Assignment.StockManagementSystem.models.Items;
import Assignment.StockManagementSystem.services.ItemsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("api/v1/reports")
public class ReportController extends AbstractController {

    private final ItemsService itemsService;

    public ReportController(ItemsService itemsService) {
        this.itemsService = itemsService;
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getReport(
            @RequestParam(required = false) String reportType,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String status) {
        try {
            switch (reportType) {
                case "sellers":
                    List<SoldoutItemCountDTO> soldItemsBySeller = itemsService.getSoldItemsBySeller();
                    return sendSuccessResponse(soldItemsBySeller, HttpStatus.OK);

                case "dateRange":
                    if (startDate == null || endDate == null) {
                        throw new BadRequestException("Start date and end date are required for this report type.");
                    }
                    LocalDateTime startDateTimeParsed = LocalDateTime.parse(startDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    LocalDateTime endDateTimeParsed = LocalDateTime.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    List<Items> soldItemsByDateRange = itemsService.getSoldItemsByDateRange(startDateTimeParsed, endDateTimeParsed);
                    return sendSuccessResponse(soldItemsByDateRange, HttpStatus.OK);

                case "status":
                    if (status == null) {
                        throw new BadRequestException("Status is required for this report type.");
                    }
                    List<Items> itemsByStatus = itemsService.getItemsByStatus(status);
                    return sendSuccessResponse(itemsByStatus, HttpStatus.OK);

                default:
                    throw new BadRequestException("Invalid report type specified.");
            }
        } catch (BadRequestException ex) {
            return sendErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException ex) {
            return sendErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (InternalServerErrorException ex) {
            return sendErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            return sendErrorResponse("An unexpected error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
