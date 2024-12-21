package Assignment.StockManagementSystem.controllers;

import Assignment.StockManagementSystem.models.Orders;
import Assignment.StockManagementSystem.services.OrdersService;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/v1/orders")
public class OrdersController extends AbstractController {

    @Autowired
    private OrdersService ordersService;

    @PostMapping
    public ResponseEntity<ResponseObject> addOrder(@RequestBody Orders order) {
        return sendCreatedResponse(ordersService.addOrder(order));
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getOrders(
            @RequestParam(required = false) String orderId,
            @RequestParam(required = false) String description,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Orders> orders = ordersService.getOrders(orderId, description, pageable);
        return sendSuccessResponse(orders);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<ResponseObject> updateOrder(
            @PathVariable String orderId,
            @RequestBody Orders updatedOrder
    ) {
        Orders result = ordersService.updateOrder(orderId, updatedOrder);
        return sendSuccessResponse(result);
    }
}
