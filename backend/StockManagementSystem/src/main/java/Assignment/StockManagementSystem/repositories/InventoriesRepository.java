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
            "WHERE (:materialName IS NULL OR :materialName = '' OR LOWER(m.materialName) LIKE LOWER(CONCAT('%', :materialName, '%'))) " +
            "AND (:sellerName IS NULL OR :sellerName = '' OR LOWER(s.sellerName) LIKE LOWER(CONCAT('%', :sellerName, '%'))) " +
            "AND (:categoryType IS NULL OR :categoryType = '' OR LOWER(c.categoryType) LIKE LOWER(CONCAT('%', :categoryType, '%')))")
    Page<Inventories> getInventories(
            @Param("materialName") String materialName,
            @Param("sellerName") String sellerName,
            @Param("categoryType") String categoryType,
            Pageable pageable);

    Optional<Inventories> findBySellerAndMaterialAndCategory(Sellers seller, Materials material, Categories category);


}
