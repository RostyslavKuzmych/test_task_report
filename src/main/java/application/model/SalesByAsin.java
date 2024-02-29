package application.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Accessors(chain = true)
@Document(collection = "salesByAsins")
public class SalesByAsin {
    private Integer unitsOrdered;
    private Integer unitsOrderedB2B;
    private OrderedProductSales orderedProductSales;
    private OrderedProductSalesB2B orderedProductSalesB2B;
    private Integer totalOrderItems;
    private Integer totalOrderItemsB2B;
}
