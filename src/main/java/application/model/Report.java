package application.model;

import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Accessors(chain = true)
@Document(collection = "reports")
public class Report {
    private List<ReportDate> salesAndTrafficByDate;
    private List<ReportAsin> salesAndTrafficByAsin;
}
