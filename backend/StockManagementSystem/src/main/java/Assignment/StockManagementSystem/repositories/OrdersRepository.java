package Assignment.StockManagementSystem.repositories;

import Assignment.StockManagementSystem.models.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders,String> {

    @Query("SELECT o FROM Orders o " +
            "WHERE (:orderId IS NULL OR o.orderId = :orderId) " +
            "AND (:description IS NULL OR o.description LIKE %:description%)")
    Page<Orders> findOrders(
            @Param("orderId") String orderId,
            @Param("description") String description,
            Pageable pageable
    );

}
