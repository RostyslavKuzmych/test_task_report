package application.model;

import java.time.LocalDate;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Accessors(chain = true)
@Document(collection = "reportsDates")
public class ReportDate {
    @Indexed(unique = true)
    private LocalDate date;
    private SalesByDate salesByDate;
    private TrafficByDate trafficByDate;
}
