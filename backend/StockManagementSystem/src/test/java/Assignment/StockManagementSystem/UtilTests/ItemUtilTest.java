package Assignment.StockManagementSystem.UtilTests;

import Assignment.StockManagementSystem.common.ErrorMessages;
import Assignment.StockManagementSystem.exceptions.InvalidSellingPriceException;
import Assignment.StockManagementSystem.utils.ItemsUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemsUtilTest {

    @Test
    void testGenerateItemCode_ValidInputs() {
        String sellerName = "Seller1";
        String materialName = "MaterialA";
        String materialType = "TypeX";
        int index = 5;

        String result = ItemsUtil.generateItemCode(sellerName, materialName, materialType, index);

        assertNotNull(result);
        assertEquals("Seller1-MaterialA-TypeX-5", result);
    }

    @Test
    void testCalculateSellingPrice_NormalStatus() {
        float buyingPrice = 100.0f;
        float profitPercentage = 20.0f;
        float salePercentage = 0.0f;
        float existingSellingPrice = 0.0f;
        String status = "normal";

        float result = ItemsUtil.calculateSellingPrice(buyingPrice, profitPercentage, salePercentage, existingSellingPrice, status);

        assertEquals(120.00001f, result);
    }

    @Test
    void testCalculateSellingPrice_SaleStatus() {
        float buyingPrice = 100.0f;
        float profitPercentage = 20.0f;
        float salePercentage = 10.0f;
        float existingSellingPrice = 0.0f;
        String status = "sale";

        float result = ItemsUtil.calculateSellingPrice(buyingPrice, profitPercentage, salePercentage, existingSellingPrice, status);

        assertEquals(110.0f, result);
    }

    @Test
    void testCalculateSellingPrice_SaleStatus_InvalidPrice() {
        float buyingPrice = 100.0f;
        float profitPercentage = 5.0f;
        float salePercentage = 10.0f;
        float existingSellingPrice = 0.0f;
        String status = "sale";

        Exception exception = assertThrows(InvalidSellingPriceException.class,
                () -> ItemsUtil.calculateSellingPrice(buyingPrice, profitPercentage, salePercentage, existingSellingPrice, status));

        assertEquals(ErrorMessages.ERR_ITEM_PRICE_ERROR, exception.getMessage());
    }

    @Test
    void testCalculateSellingPrice_StockClearingStatus() {
        float buyingPrice = 0.0f;
        float profitPercentage = 0.0f;
        float salePercentage = 0.0f;
        float existingSellingPrice = 150.0f;
        String status = "stockclearing";

        float result = ItemsUtil.calculateSellingPrice(buyingPrice, profitPercentage, salePercentage, existingSellingPrice, status);

        assertEquals(150.0f, result);
    }

    @Test
    void testCalculateSellingPrice_InvalidStatus() {
        float buyingPrice = 100.0f;
        float profitPercentage = 20.0f;
        float salePercentage = 10.0f;
        float existingSellingPrice = 0.0f;
        String status = "invalid";

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> ItemsUtil.calculateSellingPrice(buyingPrice, profitPercentage, salePercentage, existingSellingPrice, status));

        assertEquals("Invalid status: invalid", exception.getMessage());
    }
}
