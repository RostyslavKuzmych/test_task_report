package application.service;

import application.model.ReportAsin;

public interface ReportAsinService {
    ReportAsin getReportByAsin(String parentAsin);

    ReportAsin getReportByAllAsins();
}
