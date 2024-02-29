package application.service.impl;

import application.model.Report;
import application.model.ReportAsin;
import application.model.ReportDate;
import application.repository.ReportAsinRepository;
import application.repository.ReportDateRepository;
import application.service.UpdateDbService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateDbServiceImpl implements UpdateDbService {
    private final ReportDateRepository reportDateRepository;
    private final ReportAsinRepository reportAsinRepository;

    @Override
    public void addNewOrUpdatedReportsToDb(Report report) {
        addNewOrUpdatedReportsAsinToDb(report.getSalesAndTrafficByAsin());
        addNewOrUpdatedReportsDateToDb(report.getSalesAndTrafficByDate());
    }

    private void addNewOrUpdatedReportsDateToDb(List<ReportDate> reportDateList) {
        reportDateList.stream()
                .forEach(reportDate -> {
                    Optional<ReportDate> reportDateFromDb
                            = reportDateRepository.findByDate(reportDate.getDate());
                    if (reportDateFromDb.isEmpty()) {
                        reportDateRepository.save(reportDate);
                    } else {
                        if (!reportDateFromDb.get().equals(reportDate)) {
                            reportDateRepository.save(reportDate);
                        }
                    }
                });
    }

    private void addNewOrUpdatedReportsAsinToDb(List<ReportAsin> reportAsinList) {
        reportAsinList.stream()
                .forEach(reportAsin -> {
                    Optional<ReportAsin> reportAsinFromDb
                            = reportAsinRepository.findByParentAsin(reportAsin.getParentAsin());
                    if (reportAsinFromDb.isEmpty()) {
                        reportAsinRepository.save(reportAsin);
                    } else {
                        if (!reportAsinFromDb.get().equals(reportAsin)) {
                            reportAsinRepository.save(reportAsin);
                        }
                    }
                });
    }
}
