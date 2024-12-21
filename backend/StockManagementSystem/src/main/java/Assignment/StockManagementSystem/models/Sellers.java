package Assignment.StockManagementSystem.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Data
public class Sellers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sellerId;

    @NotNull(message = "Seller name cannot be null.")
    @Size(min = 1, max = 100, message = "Seller name must be between 1 and 100 characters.")
    private String sellerName;

    @Email(message = "Invalid email format.")
    @NotNull(message = "Email cannot be null.")
    private String email;

    @Size(min = 10, max = 15, message = "Contact number must be between 10 and 15 characters.")
    private String contact;

    private String address;

    @NotNull(message = "Status cannot be null.")
    private String status;

    @OneToMany(mappedBy = "seller")
    private List<Inventories> inventories;

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Inventories> getInventories() {
        return inventories;
    }

    public void setInventories(List<Inventories> inventories) {
        this.inventories = inventories;
    }
}
