package Assignment.StockManagementSystem.utils;

import Assignment.StockManagementSystem.common.ErrorMessages;
import Assignment.StockManagementSystem.exceptions.InvalidSellingPriceException;

public class ItemsUtil {

    public static String generateItemCode(String sellerName, String materialName, String materialType, int index) {
        return String.format("%s-%s-%s-%d", sellerName, materialName, materialType, index);
    }

    public static float calculateSellingPrice(float buyingPrice, float profitPercentage, float salePercentage,
                                              float existingSellingPrice, String status) {
        float sellingPrice;
        switch (status.trim().toLowerCase()) {
            case "sale":
                sellingPrice = buyingPrice * (1 + profitPercentage / 100 - salePercentage / 100);
                if (sellingPrice < buyingPrice) {
                    throw new InvalidSellingPriceException(ErrorMessages.ERR_ITEM_PRICE_ERROR);
                }
                break;
            case "normal":
                sellingPrice = buyingPrice * (1 + profitPercentage / 100);
                break;
            case "stockclearing":
                sellingPrice = existingSellingPrice;
                break;
            default:
                throw new IllegalArgumentException("Invalid status: " + status);
        }
        return sellingPrice;
    }


}
