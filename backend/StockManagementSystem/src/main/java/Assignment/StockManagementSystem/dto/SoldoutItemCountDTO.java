package Assignment.StockManagementSystem.dto;

import lombok.Data;

@Data
public class SoldoutItemCountDTO {

    private String sellerName;

    private long count;

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
