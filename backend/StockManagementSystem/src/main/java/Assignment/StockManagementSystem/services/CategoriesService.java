package Assignment.StockManagementSystem.services;

import Assignment.StockManagementSystem.dto.CategoriesDTOWithoutInventories;
import Assignment.StockManagementSystem.dto.CategoryDTOWithoutId;
import Assignment.StockManagementSystem.models.Categories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoriesService {

    public Categories addCategory(Categories category);

    public Page<CategoriesDTOWithoutInventories> getCategories(String categoryType, Pageable pageable);

    public Categories getCategoryById(int categoryId);

//    public Categories updateCategory(int categoryId, CategoryDTOWithoutId updatedCategory);

    public List<Categories> getAllCategories();
}
