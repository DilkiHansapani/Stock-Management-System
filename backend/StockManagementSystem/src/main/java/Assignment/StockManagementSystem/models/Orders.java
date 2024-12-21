package Assignment.StockManagementSystem.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class Orders {

    @Id
    private String orderId;

    private String description;

    private Date date;

    @OneToMany(mappedBy = "order")
    private List<OrderItems> orderItems;

}
