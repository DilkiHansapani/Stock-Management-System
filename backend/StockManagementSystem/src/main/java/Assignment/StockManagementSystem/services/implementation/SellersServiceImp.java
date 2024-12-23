package Assignment.StockManagementSystem.services.implementation;

import Assignment.StockManagementSystem.common.ErrorMessages;
import Assignment.StockManagementSystem.dto.SellerDTOWithoutInventories;
import Assignment.StockManagementSystem.dto.SellerDTOWitohutId;
import Assignment.StockManagementSystem.models.Sellers;
import Assignment.StockManagementSystem.repositories.SellersRepository;
import Assignment.StockManagementSystem.services.SellersService;
import Assignment.StockManagementSystem.exceptions.BadRequestException;
import Assignment.StockManagementSystem.exceptions.DuplicateResourceException;
import Assignment.StockManagementSystem.exceptions.InternalServerErrorException;
import Assignment.StockManagementSystem.exceptions.ResourceNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SellersServiceImp implements SellersService {

    private static final Logger logger = LogManager.getLogger(SellersServiceImp.class);

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private SellersRepository sellersRepository;

    @Override
    public Sellers addSeller(SellerDTOWitohutId sellerDTO) {
        try {
            Optional<Sellers> existingSeller = sellersRepository.findByEmail(sellerDTO.getEmail());
            if (existingSeller.isPresent()) {
                throw new DuplicateResourceException(ErrorMessages.ERR_MSG_DUBLICATE_VALUE);
            }
            Sellers seller = new Sellers();

            mapSellerDTOToEntity(sellerDTO, seller);

            seller.setStatus("Active");
            return sellersRepository.save(seller);
        } catch (DuplicateResourceException ex) {
            logger.error("Seller Email Already Exist", ex);
            throw new DuplicateResourceException(ErrorMessages.ERR_MSG_DUBLICATE_VALUE);
        } catch (Exception ex) {
            logger.error("Error occurred adding seller {}", sellerDTO.getEmail(), ex);
            throw new InternalServerErrorException(ErrorMessages.ERR_MSG_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Page<SellerDTOWithoutInventories> getSellers(String searchTerm, Pageable pageable) {
        try {
            if (searchTerm == null || searchTerm.trim().isEmpty()) {
                return sellersRepository.findSellers(null, pageable)
                        .map(this::convertToSellerDTO);
            }

            Page<Sellers> sellers = sellersRepository.findSellers(searchTerm, pageable);
            return sellers.map(this::convertToSellerDTO);
        } catch (Exception ex) {
            logger.error("Error occurred retrieving sellers", ex);
            throw new InternalServerErrorException(ErrorMessages.ERR_MSG_INTERNAL_SERVER_ERROR);
        }
    }



    @Override
    public Sellers getSellerById(int sellerId) {
        try{
            return sellersRepository.findById(sellerId)
                    .orElseThrow(() -> new ResourceNotFoundException("Seller not found with ID: " + sellerId));
        }catch (ResourceNotFoundException ex){
            logger.error("Seller not found with id :"+sellerId);
            throw ex;
        }catch (Exception ex){
            logger.error("Internal server error");
            throw new InternalServerErrorException(ErrorMessages.ERR_MSG_INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public Sellers updateSeller(int sellerId, SellerDTOWitohutId updatedSeller) {
        try {
            if (sellerId <= 0) {
                throw new BadRequestException("Seller ID must be a valid number.");
            }

            Sellers existingSeller = sellersRepository.findById(sellerId)
                    .orElseThrow(() -> new ResourceNotFoundException("Seller with ID " + sellerId + " not found."));

            if (updatedSeller.getEmail() != null && !updatedSeller.getEmail().equals(existingSeller.getEmail())) {
                Optional<Sellers> emailExists = sellersRepository.findByEmail(updatedSeller.getEmail());
                if (emailExists.isPresent()) {
                    throw new DuplicateResourceException("Email " + updatedSeller.getEmail() + " is already in use.");
                }
            }

            mapSellerDTOToEntity(updatedSeller, existingSeller);
            return sellersRepository.save(existingSeller);
        } catch (ResourceNotFoundException | BadRequestException | DuplicateResourceException ex) {
            throw ex;
        } catch (Exception ex) {
            logger.error("Error occurred while updating seller with ID " + sellerId, ex);
            throw new InternalServerErrorException("An error occurred while updating the seller. Please try again.");
        }
    }

    private SellerDTOWithoutInventories convertToSellerDTO(Sellers seller) {
        SellerDTOWithoutInventories dto = new SellerDTOWithoutInventories();
        dto.setSellerId(seller.getSellerId());
        dto.setSellerName(seller.getSellerName());
        dto.setEmail(seller.getEmail());
        dto.setAddress(seller.getAddress());
        dto.setContact(seller.getContact());
        dto.setStatus(seller.getStatus());
        return dto;
    }

    private void mapSellerDTOToEntity(SellerDTOWitohutId sellerDTO, Sellers seller) {
        seller.setSellerName(sellerDTO.getSellerName());
        seller.setEmail(sellerDTO.getEmail());
        seller.setContact(sellerDTO.getContact());
        seller.setAddress(sellerDTO.getAddress());
        seller.setStatus(sellerDTO.getStatus() != null ? sellerDTO.getStatus() : "Active");
    }

}
