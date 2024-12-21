package Assignment.StockManagementSystem.services.implementation;

import Assignment.StockManagementSystem.common.ErrorMessages;
import Assignment.StockManagementSystem.dto.CategoriesDTOWithoutInventories;
import Assignment.StockManagementSystem.dto.CategoryDTOWithoutId;
import Assignment.StockManagementSystem.models.Categories;
import Assignment.StockManagementSystem.repositories.CategoriesRepository;
import Assignment.StockManagementSystem.services.CategoriesService;
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
public class CategoriesServiceImp implements CategoriesService {

    private static final Logger logger = LogManager.getLogger(CategoriesServiceImp.class);

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Override
    public Categories addCategory(Categories category) {
        try {
            Optional<Categories> existingCategory = categoriesRepository.findByCategoryType(
                    category.getCategoryType()
            );

            if (existingCategory.isPresent()) {
                throw new DuplicateResourceException("Category type already exists");
            }

            return categoriesRepository.save(category);
        } catch (DuplicateResourceException ex){
            logger.error("Category type already exists",ex);
            throw ex;
        }catch (Exception ex) {
            logger.error("Error occurred adding category", ex);
            throw new InternalServerErrorException(ErrorMessages.ERR_MSG_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Page<CategoriesDTOWithoutInventories> getCategories(String categoryType, Pageable pageable) {
        try {
            Page<Categories> categories =  categoriesRepository.findCategories(categoryType, pageable);

            return categories.map(this::convertToCategoryDTO);
        } catch (Exception ex) {
            logger.error("Error occurred retrieving categories", ex);
            throw new InternalServerErrorException(ErrorMessages.ERR_MSG_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Categories getCategoryById(int categoryId) {
        try {
            return categoriesRepository.findById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + categoryId));
        }catch (ResourceNotFoundException ex){
            logger.error("Category not found with ID: " + categoryId);
            throw ex;
        }catch (Exception ex) {
            logger.error("Error occurred retrieving categories", ex);
            throw new InternalServerErrorException(ErrorMessages.ERR_MSG_INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public Categories updateCategory(int categoryId, CategoryDTOWithoutId updatedCategory) {
        try {
            if (categoryId <= 0) {
                throw new BadRequestException("Category ID must be a valid number.");
            }

            Categories existingCategory = categoriesRepository.findById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Category with ID " + categoryId + " not found."));

            modelMapper.map(updatedCategory, existingCategory);

            return categoriesRepository.save(existingCategory);
        } catch (ResourceNotFoundException | BadRequestException ex) {
            throw ex;
        } catch (Exception ex) {
            logger.error("Error occurred while updating category with ID " + categoryId, ex);
            throw new InternalServerErrorException("An error occurred while updating the category. Please try again.");
        }
    }

    private CategoriesDTOWithoutInventories convertToCategoryDTO(Categories category) {
        CategoriesDTOWithoutInventories dto = new CategoriesDTOWithoutInventories();
        dto.setCategoryId(category.getCategoryId());
        dto.setCategoryType(category.getCategoryType());
        return dto;
    }
}
