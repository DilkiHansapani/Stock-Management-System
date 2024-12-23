package Assignment.StockManagementSystem.services.implementation;

import Assignment.StockManagementSystem.common.ErrorMessages;
import Assignment.StockManagementSystem.models.Orders;
import Assignment.StockManagementSystem.repositories.OrdersRepository;
import Assignment.StockManagementSystem.services.OrdersService;
import Assignment.StockManagementSystem.exceptions.BadRequestException;
import Assignment.StockManagementSystem.exceptions.InternalServerErrorException;
import Assignment.StockManagementSystem.exceptions.ResourceNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OrdersServiceImp implements OrdersService {

    private static final Logger logger = LogManager.getLogger(OrdersServiceImp.class);

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private OrdersRepository ordersRepository;

    @Override
    public Orders addOrder(Orders order) {
        try {
            return ordersRepository.save(order);
        } catch (Exception ex) {
            logger.error("Error occurred adding order", ex);
            throw new InternalServerErrorException(ErrorMessages.ERR_MSG_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Page<Orders> getOrders(String orderId, String description, Pageable pageable) {
        try {
            orderId = (orderId == null) ? "" : orderId;
            description = (description == null) ? "" : description;

            return ordersRepository.findOrders(orderId, description, pageable);
        } catch (Exception ex) {
            logger.error("Error occurred retrieving orders", ex);
            throw new InternalServerErrorException(ErrorMessages.ERR_MSG_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Orders updateOrder(String orderId, Orders updatedOrder) {
        try {
            if (orderId == null || orderId.isEmpty()) {
                throw new BadRequestException("Order ID must be provided.");
            }

            Orders existingOrder = ordersRepository.findById(orderId)
                    .orElseThrow(() -> new ResourceNotFoundException("Order with ID " + orderId + " not found."));

            modelMapper.map(updatedOrder, existingOrder);

            return ordersRepository.save(existingOrder);
        } catch (ResourceNotFoundException | BadRequestException ex) {
            throw ex;
        } catch (Exception ex) {
            logger.error("Error occurred while updating order with ID " + orderId, ex);
            throw new InternalServerErrorException("An error occurred while updating the order. Please try again.");
        }
    }
}
