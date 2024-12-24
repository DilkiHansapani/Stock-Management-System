package Assignment.StockManagementSystem.controllers;

import Assignment.StockManagementSystem.dto.CategoriesDTOWithoutInventories;
import Assignment.StockManagementSystem.dto.CategoryDTOWithoutId;
import Assignment.StockManagementSystem.models.Categories;
import Assignment.StockManagementSystem.services.CategoriesService;
import Assignment.StockManagementSystem.exceptions.BadRequestException;
import Assignment.StockManagementSystem.exceptions.DuplicateResourceException;
import Assignment.StockManagementSystem.exceptions.InternalServerErrorException;
import Assignment.StockManagementSystem.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/v1/categories")
public class CategoriesController extends AbstractController {

    @Autowired
    private CategoriesService categoriesService;

    @PostMapping
    public ResponseEntity<ResponseObject> addCategory(@RequestBody Categories category) {
        try {
            return sendCreatedResponse(categoriesService.addCategory(category),HttpStatus.CREATED);
        } catch (DuplicateResourceException ex) {
            return sendErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
        } catch (InternalServerErrorException ex) {
            return sendErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getCategories(
            @RequestParam(required = false) String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Page<CategoriesDTOWithoutInventories> categories = categoriesService.getCategories(searchTerm, PageRequest.of(page, size));
            return sendSuccessResponse(categories,HttpStatus.OK);
        } catch (InternalServerErrorException ex) {
            return sendErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public  ResponseEntity<ResponseObject> getAllCategories(){
        try {
            return sendSuccessResponse(categoriesService.getAllCategories(), HttpStatus.OK);
        }catch (InternalServerErrorException ex) {
            return sendErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<ResponseObject> updateCategory(
            @PathVariable int categoryId,
            @RequestBody CategoryDTOWithoutId updatedCategory
    ) {
        try {
            Categories result = categoriesService.updateCategory(categoryId, updatedCategory);
            return sendSuccessResponse(result,HttpStatus.OK);
        } catch (BadRequestException ex) {
            return sendErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException ex) {
            return sendErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (InternalServerErrorException ex) {
            return sendErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
