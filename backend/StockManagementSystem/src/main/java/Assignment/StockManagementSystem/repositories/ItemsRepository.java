package Assignment.StockManagementSystem.repositories;

import Assignment.StockManagementSystem.models.Inventories;
import Assignment.StockManagementSystem.models.Items;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface ItemsRepository extends JpaRepository<Items,String> {

    @Query("SELECT i FROM Items i WHERE " +
            "(:itemCode IS NULL OR :itemCode = '' OR i.itemCode LIKE %:itemCode%) AND " +
            "(:startDateTime IS NULL OR i.dateTime >= :startDateTime) AND " +
            "(:endDateTime IS NULL OR i.dateTime <= :endDateTime) AND " +
            "(:status IS NULL OR :status = '' OR i.status = :status)")
    Page<Items> findItems(@Param("itemCode") String itemCode,
                          @Param("startDateTime") LocalDateTime startDateTime,
                          @Param("endDateTime") LocalDateTime endDateTime,
                          @Param("status") String status,
                          Pageable pageable);


    @Query("SELECT i FROM Items i WHERE i.inventory = :inventory ORDER BY i.dateTime ASC LIMIT :bulkQuantity")
    List<Items> findFirstNByInventoryOrderByDateTimeAsc(@Param("inventory") Inventories inventory, @Param("bulkQuantity") int bulkQuantity);

    @Query("SELECT i.inventory.seller, COUNT(i) " +
            "FROM Items i " +
            "WHERE i.status = 'soldout' " +
            "GROUP BY i.inventory.seller "+
            "ORDER BY COUNT(i) DESC")
    List<Object[]> countSoldOutItemsBySeller();

    @Query("SELECT i FROM Items i WHERE i.status = :status")
    List<Items> findItemsByStatus(@Param("status") String status);

    @Query("SELECT i FROM Items i WHERE " +
            "(:startDateTime IS NULL OR i.dateTime >= :startDateTime) AND " +
            "(:endDateTime IS NULL OR i.dateTime <= :endDateTime) AND " +
            "(i.status = 'soldout')")
    List<Items> findSoldoutItemDateRange(
                          @Param("startDateTime") LocalDateTime startDateTime,
                          @Param("endDateTime") LocalDateTime endDateTime);


}
