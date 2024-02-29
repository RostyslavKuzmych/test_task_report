package application.controller;

import application.model.ReportAsin;
import application.model.ReportDate;
import application.service.ReportAsinService;
import application.service.ReportDateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/report")
@Tag(name = "Report management", description = "Endpoint for getting report")
public class ReportController {
    private final ReportDateService reportDateService;
    private final ReportAsinService reportAsinService;

    @PostMapping("/date")
    @Operation(summary = "Get report for concrete date",
            description = "Endpoint for getting report for concrete date")
    @ResponseStatus(HttpStatus.OK)
    public ReportDate getReportByDate(@RequestBody String stringLocalDate) {
        return reportDateService.getReportByDate(LocalDate.parse(stringLocalDate));
    }

    @GetMapping("/date")
    @Operation(summary = "Get report for all dates",
            description = "Endpoint for getting report for all dates")
    @ResponseStatus(HttpStatus.OK)
    public ReportDate getReportByAllDates() {
        return reportDateService.getReportByAllDates();
    }

    @PostMapping("/asin")
    @Operation(summary = "Get report for concrete asin",
            description = "Endpoint for getting report for concrete asin")
    @ResponseStatus(HttpStatus.OK)
    public ReportAsin getReportByAsin(@RequestBody String parentAsin) {
        return reportAsinService.getReportByAsin(parentAsin);
    }

    @GetMapping("/asin")
    @Operation(summary = "Get report for all asins",
            description = "Endpoint for getting report for all asis")
    @ResponseStatus(HttpStatus.OK)
    public ReportAsin getReportByAllAsins() {
        return reportAsinService.getReportByAllAsins();
    }
}
