package Assignment.StockManagementSystem.controllers;

import Assignment.StockManagementSystem.dto.ItemDTOUpdate;
import Assignment.StockManagementSystem.dto.ItemsDTOWithoutInventories;
import Assignment.StockManagementSystem.dto.SoldoutItemCountDTO;
import Assignment.StockManagementSystem.models.Items;
import Assignment.StockManagementSystem.services.ItemsService;
import Assignment.StockManagementSystem.exceptions.BadRequestException;
import Assignment.StockManagementSystem.exceptions.ResourceNotFoundException;
import Assignment.StockManagementSystem.exceptions.InternalServerErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@RestController
@RequestMapping("api/v1/items")
public class ItemsController extends AbstractController {

    @Autowired
    private ItemsService itemsService;

    @GetMapping
    public ResponseEntity<ResponseObject> getItems(
            @RequestParam(required = false) String itemCode,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            LocalDateTime startDateTimeParsed = null;
            LocalDateTime endDateTimeParsed = null;

            if (startDateTime != null && !startDateTime.isEmpty()) {
                try {
                    startDateTimeParsed = LocalDateTime.parse(startDateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                } catch (Exception e) {
                    throw new BadRequestException("Invalid startDateTime format. Please use 'yyyy-MM-dd'T'HH:mm:ss'");
                }
            }

            if (endDateTime != null && !endDateTime.isEmpty()) {
                try {
                    endDateTimeParsed = LocalDateTime.parse(endDateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                } catch (Exception e) {
                    System.out.println("Error parsing endDateTime: " + endDateTime);
                    throw new BadRequestException("Invalid endDateTime format. Please use 'yyyy-MM-dd'T'HH:mm:ss'");
                }
            }
            
            Pageable pageable = PageRequest.of(page, size);
            Page<ItemsDTOWithoutInventories> items = itemsService.getItems(
                    itemCode, startDateTimeParsed, endDateTimeParsed, status, pageable);

            return sendSuccessResponse(items, HttpStatus.OK);
        } catch (BadRequestException ex) {
            return sendErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerErrorException ex) {
            return sendErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            return sendErrorResponse("An unexpected error occurred.", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{itemCode}")
    public ResponseEntity<ResponseObject> updateItem(
            @PathVariable String itemCode,
            @RequestBody ItemDTOUpdate updatedItem) {
        try {
            Items result = itemsService.updateItem(itemCode, updatedItem);
            return sendSuccessResponse(result, HttpStatus.OK);
        } catch (BadRequestException ex) {
            return sendErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException ex) {
            return sendErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (InternalServerErrorException ex) {
            return sendErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/reports/sold-items/by-seller")
    public ResponseEntity<ResponseObject> getSoldItemsBySeller() {
        try {
            List<SoldoutItemCountDTO> soldItemsBySeller = itemsService.getSoldItemsBySeller();

            return sendSuccessResponse(soldItemsBySeller, HttpStatus.OK);
        } catch (BadRequestException ex) {
            return sendErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerErrorException ex) {
            return sendErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            return sendErrorResponse("An unexpected error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/reports/sold-items/date-range")
    public ResponseEntity<ResponseObject> getSoldItemsByDateRange(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        try {
            LocalDateTime startDateTimeParsed = LocalDateTime.parse(startDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            LocalDateTime endDateTimeParsed = LocalDateTime.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

            List<Items> soldItemsByDateRange = itemsService.getSoldItemsByDateRange(startDateTimeParsed, endDateTimeParsed);

            return sendSuccessResponse(soldItemsByDateRange, HttpStatus.OK);
        } catch (BadRequestException ex) {
            return sendErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerErrorException ex) {
            return sendErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            return sendErrorResponse("An unexpected error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/reports/status")
    public ResponseEntity<ResponseObject> getItemsByStatus(
            @RequestParam String status) {
        try {
            List<Items> itemsByStatus = itemsService.getItemsByStatus(status);

            return sendSuccessResponse(itemsByStatus, HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            return sendErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (InternalServerErrorException ex) {
            return sendErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            return sendErrorResponse("An unexpected error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}