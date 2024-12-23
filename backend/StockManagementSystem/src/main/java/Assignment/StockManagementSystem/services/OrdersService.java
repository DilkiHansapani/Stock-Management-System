package Assignment.StockManagementSystem.services;

import Assignment.StockManagementSystem.models.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface OrdersService {
    Orders addOrder(Orders order);

    Page<Orders> getOrders(String orderId, String description, Pageable pageable);

    Orders updateOrder(String orderId, Orders updatedOrder);
}
