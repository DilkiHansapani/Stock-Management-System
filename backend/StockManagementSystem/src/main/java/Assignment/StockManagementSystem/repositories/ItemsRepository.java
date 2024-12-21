package Assignment.StockManagementSystem.repositories;

import Assignment.StockManagementSystem.models.Items;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

@Repository
public interface ItemsRepository extends JpaRepository<Items,String> {

    @Query("SELECT i FROM Items i WHERE " +
            "(:itemCode IS NULL OR :itemCode = '' OR i.itemCode LIKE %:itemCode%) AND " +
            "(:startDateTime IS NULL OR i.dateTime >= :startDateTime) AND " +
            "(:endDateTime IS NULL OR i.dateTime <= :endDateTime) AND " +
            "(:status IS NULL OR :status = '' OR i.status = :status)")
    Page<Items> findItems(@Param("itemCode") String itemCode,
                          @Param("startDateTime") Date startDateTime,
                          @Param("endDateTime") Date endDateTime,
                          @Param("status") String status,
                          Pageable pageable);


}
