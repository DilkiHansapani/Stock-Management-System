package Assignment.StockManagementSystem.ServiceTests;

import Assignment.StockManagementSystem.dto.CategoriesDTOWithoutInventories;
import Assignment.StockManagementSystem.exceptions.DuplicateResourceException;
import Assignment.StockManagementSystem.exceptions.ResourceNotFoundException;
import Assignment.StockManagementSystem.models.Categories;
import Assignment.StockManagementSystem.repositories.CategoriesRepository;
import Assignment.StockManagementSystem.services.implementation.CategoriesServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoriesServiceImpTest {

    @InjectMocks
    private CategoriesServiceImp categoriesService;

    @Mock
    private CategoriesRepository categoriesRepository;

    private Categories category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        category = new Categories();
        category.setCategoryId(1);
        category.setCategoryType("Test Category");
    }

    @Test
    void testAddCategorySuccess() {
        when(categoriesRepository.findByCategoryType(category.getCategoryType()))
                .thenReturn(Optional.empty());
        when(categoriesRepository.save(any(Categories.class))).thenReturn(category);

        Categories result = categoriesService.addCategory(category);

        assertNotNull(result);
        assertEquals(category.getCategoryType(), result.getCategoryType());
        verify(categoriesRepository, times(1)).save(any(Categories.class));
    }

    @Test
    void testAddCategoryDuplicateResourceException() {
        when(categoriesRepository.findByCategoryType(category.getCategoryType()))
                .thenReturn(Optional.of(category));

        assertThrows(DuplicateResourceException.class, () -> categoriesService.addCategory(category));
        verify(categoriesRepository, never()).save(any(Categories.class));
    }

    @Test
    void testGetCategoriesWithSearchTerm() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Categories> page = new PageImpl<>(Collections.singletonList(category));

        when(categoriesRepository.findCategories("Test", pageable)).thenReturn(page);

        Page<CategoriesDTOWithoutInventories> result = categoriesService.getCategories("Test", pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(categoriesRepository, times(1)).findCategories("Test", pageable);
    }

    @Test
    void testGetCategoriesWithoutSearchTerm() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Categories> page = new PageImpl<>(Collections.singletonList(category));

        when(categoriesRepository.findCategories(null, pageable)).thenReturn(page);

        Page<CategoriesDTOWithoutInventories> result = categoriesService.getCategories(null, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(categoriesRepository, times(1)).findCategories(null, pageable);
    }

    @Test
    void testGetCategoryByIdSuccess() {
        when(categoriesRepository.findById(1)).thenReturn(Optional.of(category));

        Categories result = categoriesService.getCategoryById(1);

        assertNotNull(result);
        assertEquals(category.getCategoryType(), result.getCategoryType());
        verify(categoriesRepository, times(1)).findById(1);
    }

    @Test
    void testGetCategoryByIdNotFound() {
        when(categoriesRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoriesService.getCategoryById(1));
        verify(categoriesRepository, times(1)).findById(1);
    }

    @Test
    void testGetAllCategoriesSuccess() {
        when(categoriesRepository.findAll()).thenReturn(Collections.singletonList(category));

        List<Categories> result = categoriesService.getAllCategories();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(categoriesRepository, times(1)).findAll();
    }

}
