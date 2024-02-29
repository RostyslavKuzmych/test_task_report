package application.service;

import application.model.ReportDate;
import java.time.LocalDate;

public interface ReportDateService {
    ReportDate getReportByDate(LocalDate localDate);

    ReportDate getReportByAllDates();
}
