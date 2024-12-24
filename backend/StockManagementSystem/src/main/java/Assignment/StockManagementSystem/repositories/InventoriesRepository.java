package Assignment.StockManagementSystem.repositories;

import Assignment.StockManagementSystem.models.Categories;
import Assignment.StockManagementSystem.models.Inventories;
import Assignment.StockManagementSystem.models.Materials;
import Assignment.StockManagementSystem.models.Sellers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoriesRepository extends JpaRepository<Inventories,Integer> {

    @Query("SELECT i FROM Inventories i " +
            "JOIN i.material m " +
            "JOIN i.seller s " +
            "JOIN i.category c " +
            "WHERE (:searchTerm IS NULL OR :searchTerm = '' OR " +
            "m.materialType LIKE %:searchTerm% OR m.materialName LIKE %:searchTerm%) OR s.sellerName LIKE %:searchTerm% OR c.categoryType LIKE %:searchTerm%")
    Page<Inventories> getInventories(
            @Param("searchTerm") String searchTerm,
            Pageable pageable);

    Optional<Inventories> findBySellerAndMaterialAndCategory(Sellers seller, Materials material, Categories category);


}
