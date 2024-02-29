package application.model;

import java.math.BigDecimal;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Accessors(chain = true)
@Document(collection = "salesByDates")
public class SalesByDate {
    private OrderedProductSales orderedProductSales;
    private OrderedProductSalesB2B orderedProductSalesB2B;
    private Integer unitsOrdered;
    private Integer unitsOrderedB2B;
    private Integer totalOrderItems;
    private Integer totalOrderItemsB2B;
    private AverageSalesPerOrderItem averageSalesPerOrderItem;
    private AverageSalesPerOrderItemB2B averageSalesPerOrderItemB2B;
    private BigDecimal averageUnitsPerOrderItem;
    private BigDecimal averageUnitsPerOrderItemB2B;
    private AverageSellingPrice averageSellingPrice;
    private AverageSellingPriceB2B averageSellingPriceB2B;
    private Integer unitsRefunded;
    private BigDecimal refundRate;
    private Integer claimsGranted;
    private ClaimsAmount claimsAmount;
    private Integer unitsShipped;
    private Integer ordersShipped;
}
