package Assignment.StockManagementSystem.repositories;

import Assignment.StockManagementSystem.models.Categories;
import Assignment.StockManagementSystem.models.Materials;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Integer> {

    @Query("SELECT c FROM Categories c " +
            "WHERE (:categoryType IS NULL OR :categoryType = '' OR c.categoryType LIKE %:categoryType%)")
    Page<Categories> findCategories(
            @Param("categoryType") String categoryType,
            Pageable pageable
    );

    Optional<Categories> findByCategoryType(String categoryType);

}
