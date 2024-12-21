package Assignment.StockManagementSystem.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class OrderItems {

    @Id
    private String orderItemId;

    @ManyToOne
    @JoinColumn(name = "orderId", nullable = false)
    private Orders order;


    private float totalPrice;

}
