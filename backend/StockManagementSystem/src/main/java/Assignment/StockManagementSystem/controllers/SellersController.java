package Assignment.StockManagementSystem.controllers;

import Assignment.StockManagementSystem.dto.SellerDTOWitohutId;
import Assignment.StockManagementSystem.services.SellersService;
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
@RequestMapping("api/v1/sellers")
public class SellersController extends AbstractController {

    @Autowired
    private SellersService sellersService;

    @PostMapping
    public ResponseEntity<ResponseObject> addSeller(@RequestBody SellerDTOWitohutId seller) {
        try {
            System.out.println("add seller :"+seller);
            return sendCreatedResponse(sellersService.addSeller(seller),HttpStatus.CREATED);
        } catch (DuplicateResourceException ex) {
            return sendErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
        } catch (InternalServerErrorException ex) {
            return sendErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getSellers(
            @RequestParam(required = false) String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            return sendSuccessResponse(sellersService.getSellers(searchTerm, PageRequest.of(page, size)),HttpStatus.OK);
        } catch (InternalServerErrorException ex) {
            return sendErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseObject> getAllSellers(){
        try{
            return sendSuccessResponse(sellersService.getAllSellers(),HttpStatus.OK);
        }catch (InternalServerErrorException ex) {
            return sendErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{sellerId}")
    public ResponseEntity<ResponseObject> updateSeller(
            @PathVariable int sellerId,
            @RequestBody SellerDTOWitohutId updatedSeller
    ) {
        try {
            return sendSuccessResponse(sellersService.updateSeller(sellerId, updatedSeller),HttpStatus.OK);
        } catch (BadRequestException ex) {
            return sendErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException ex) {
            return sendErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DuplicateResourceException ex) {
            return sendErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
        } catch (InternalServerErrorException ex) {
            return sendErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
