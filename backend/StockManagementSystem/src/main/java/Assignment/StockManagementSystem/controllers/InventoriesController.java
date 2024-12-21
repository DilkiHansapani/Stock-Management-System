package Assignment.StockManagementSystem.controllers;

import Assignment.StockManagementSystem.dto.InventoryDTOWithoutId;
import Assignment.StockManagementSystem.repositories.InventoriesRepository;
import Assignment.StockManagementSystem.services.InventoriesService;
import Assignment.StockManagementSystem.exceptions.BadRequestException;
import Assignment.StockManagementSystem.exceptions.InternalServerErrorException;
import Assignment.StockManagementSystem.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/v1/inventories")
public class InventoriesController extends AbstractController {

    @Autowired
    private InventoriesService inventoriesService;

    @Autowired
    InventoriesRepository inventoriesRepository;

    @PostMapping
    public ResponseEntity<ResponseObject> addInventory(@RequestBody InventoryDTOWithoutId inventory) {
        try {
            return sendCreatedResponse(inventoriesService.addInventory(inventory));
        } catch (InternalServerErrorException ex) {
            return sendErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping
//    public ResponseEntity<ResponseObject> getInventories(
//            @RequestParam(required = false) String materialName,
//            @RequestParam(required = false) String sellerName,
//            @RequestParam(required = false) String categoryType,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size
//    ) {
//        try {
//            return sendSuccessResponse(inventoriesRepository.findAll());
//        } catch (InternalServerErrorException ex) {
//            return sendErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @GetMapping
    public ResponseEntity<ResponseObject> getInventories(
            @RequestParam(required = false) String materialName,
            @RequestParam(required = false) String sellerName,
            @RequestParam(required = false) String categoryType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            return sendSuccessResponse(inventoriesService.getInventories(materialName, sellerName, categoryType, PageRequest.of(page, size)));
        } catch (InternalServerErrorException ex) {
            return sendErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{inventoryId}")
    public ResponseEntity<ResponseObject> updateInventory(
            @PathVariable int inventoryId,
            @RequestBody InventoryDTOWithoutId updatedInventory
    ) {
        try {
            return sendSuccessResponse(inventoriesService.updateInventory(inventoryId, updatedInventory));
        } catch (BadRequestException ex) {
            return sendErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException ex) {
            return sendErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (InternalServerErrorException ex) {
            return sendErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
