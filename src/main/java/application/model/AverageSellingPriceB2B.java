package application.model;

import java.math.BigDecimal;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Accessors(chain = true)
@Document(collection = "averageSellingPricesB2B")
public class AverageSellingPriceB2B {
    private BigDecimal amount;
    private String currencyCode;
}
