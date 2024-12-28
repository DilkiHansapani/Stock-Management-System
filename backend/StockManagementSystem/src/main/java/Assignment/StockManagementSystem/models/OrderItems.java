package Assignment.StockManagementSystem.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class OrderItems {

    @Id
    private String orderItemId;

    @OneToOne
    @JoinColumn(name = "itemCode", referencedColumnName = "itemCode", nullable = false)
    private Items item;

    @ManyToOne
    @JoinColumn(name = "orderId", nullable = false)
    private Orders order;


    private float totalPrice;

}
