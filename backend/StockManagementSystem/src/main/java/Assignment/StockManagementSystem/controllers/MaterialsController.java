package Assignment.StockManagementSystem.controllers;

import Assignment.StockManagementSystem.dto.MaterialDTOWithoutId;
import Assignment.StockManagementSystem.services.MaterialsService;
import Assignment.StockManagementSystem.exceptions.BadRequestException;
import Assignment.StockManagementSystem.exceptions.DuplicateResourceException;
import Assignment.StockManagementSystem.exceptions.InternalServerErrorException;
import Assignment.StockManagementSystem.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/v1/materials")
public class MaterialsController extends AbstractController {

    @Autowired
    private MaterialsService materialsService;

    @PostMapping
    public ResponseEntity<ResponseObject> addMaterial(@RequestBody MaterialDTOWithoutId material) {
        try {
            return sendCreatedResponse(materialsService.addMaterial(material),HttpStatus.CREATED);
        } catch (DuplicateResourceException ex) {
            return sendErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
        } catch (InternalServerErrorException ex) {
            return sendErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getMaterials(
            @RequestParam(required = false) String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            return sendSuccessResponse(materialsService.getMaterials(searchTerm, PageRequest.of(page, size)),HttpStatus.OK);
        } catch (InternalServerErrorException ex) {
            return sendErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{materialId}")
    public ResponseEntity<ResponseObject> updateMaterial(
            @PathVariable int materialId,
            @RequestBody MaterialDTOWithoutId updatedMaterial
    ) {
        try {
            return sendSuccessResponse(materialsService.updateMaterial(materialId, updatedMaterial),HttpStatus.OK);
        } catch (BadRequestException ex) {
            return sendErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException ex) {
            return sendErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (InternalServerErrorException ex) {
            return sendErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
