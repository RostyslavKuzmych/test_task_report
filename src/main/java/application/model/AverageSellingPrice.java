package application.model;

import java.math.BigDecimal;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Accessors(chain = true)
@Document(collection = "averageSellingPrices")
public class AverageSellingPrice {
    private BigDecimal amount;
    private String currencyCode;
}
