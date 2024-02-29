package application.service.impl;

import application.exception.EntityNotFoundException;
import application.model.OrderedProductSales;
import application.model.OrderedProductSalesB2B;
import application.model.Report;
import application.model.ReportAsin;
import application.model.SalesByAsin;
import application.model.TrafficByAsin;
import application.repository.ReportAsinRepository;
import application.service.JsonReaderService;
import application.service.ReportAsinService;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportAsinServiceImpl implements ReportAsinService {
    private static final String FIND_EXCEPTION = "Can't find a reportAsin by parentAsin ";
    private final JsonReaderService jsonReaderService;
    private final ReportAsinRepository reportAsinRepository;

    @Override
    @Cacheable(value = "allResponses", unless = "#result == null")
    public ReportAsin getReportByAsin(String parentAsin) {
        return reportAsinRepository.findByParentAsin(parentAsin)
                .orElseThrow(() -> new EntityNotFoundException(FIND_EXCEPTION + parentAsin));
    }

    @Override
    public ReportAsin getReportByAllAsins() {
        Report report = jsonReaderService.readFile();
        return new ReportAsin().setSalesByAsin(getSalesByAsin(report.getSalesAndTrafficByAsin()))
                .setTrafficByAsin(getTrafficByAsin(report.getSalesAndTrafficByAsin()));
    }

    private SalesByAsin getSalesByAsin(List<ReportAsin> reportAsinList) {
        return new SalesByAsin().setOrderedProductSales(getOrderedProductSales(reportAsinList))
                .setOrderedProductSalesB2B(getOrderedProductSalesB2B(reportAsinList))
                .setUnitsOrdered(getUnitsOrdered(reportAsinList))
                .setUnitsOrderedB2B(getUnitsOrderedB2B(reportAsinList))
                .setTotalOrderItems(getTotalOrderItems(reportAsinList))
                .setTotalOrderItemsB2B(getTotalOrderItemsB2B(reportAsinList));
    }

    private Integer getTotalOrderItemsB2B(List<ReportAsin> reportAsinList) {
        return reportAsinList
                .stream()
                .map(reportAsin -> reportAsin.getSalesByAsin().getTotalOrderItemsB2B())
                .mapToInt(Integer::intValue)
                .sum();
    }

    private Integer getTotalOrderItems(List<ReportAsin> reportAsinList) {
        return reportAsinList
                .stream()
                .map(reportAsin -> reportAsin.getSalesByAsin().getTotalOrderItems())
                .mapToInt(Integer::intValue)
                .sum();
    }

    private Integer getUnitsOrderedB2B(List<ReportAsin> reportAsinList) {
        return reportAsinList
                .stream()
                .map(reportAsin -> reportAsin.getSalesByAsin().getUnitsOrderedB2B())
                .mapToInt(Integer::intValue)
                .sum();
    }

    private Integer getUnitsOrdered(List<ReportAsin> reportAsinList) {
        return reportAsinList
                .stream()
                .map(reportAsin -> reportAsin.getSalesByAsin().getUnitsOrdered())
                .mapToInt(Integer::intValue)
                .sum();
    }

    private OrderedProductSalesB2B getOrderedProductSalesB2B(List<ReportAsin> reportAsinList) {
        BigDecimal amount = reportAsinList.stream()
                .map(reportAsin -> reportAsin.getSalesByAsin()
                        .getOrderedProductSalesB2B().getAmount())
                .mapToDouble(BigDecimal::doubleValue)
                .mapToObj(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        String currencyCode = reportAsinList.stream().map(reportAsin
                -> reportAsin.getSalesByAsin().getOrderedProductSalesB2B().getCurrencyCode())
                .findFirst().get();
        return new OrderedProductSalesB2B().setCurrencyCode(currencyCode).setAmount(amount);
    }

    private OrderedProductSales getOrderedProductSales(List<ReportAsin> reportAsinList) {
        BigDecimal amount = reportAsinList.stream()
                .map(reportAsin -> reportAsin.getSalesByAsin()
                        .getOrderedProductSales().getAmount())
                .mapToDouble(BigDecimal::doubleValue)
                .mapToObj(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        String currencyCode = reportAsinList.stream().map(reportAsin
                        -> reportAsin.getSalesByAsin().getOrderedProductSales().getCurrencyCode())
                .findFirst().get();
        return new OrderedProductSales().setCurrencyCode(currencyCode).setAmount(amount);
    }

    private TrafficByAsin getTrafficByAsin(List<ReportAsin> reportAsinList) {
        return new TrafficByAsin()
                .setBrowserPageViewsPercentage(getBrowserPageViewsPercentage(reportAsinList))
                .setBrowserPageViewsB2B(getBrowserPageViewsB2B(reportAsinList))
                .setBrowserSessions(getBrowserSessions(reportAsinList))
                .setBrowserSessionsB2B(getBrowserSessionsB2B(reportAsinList))
                .setMobileAppSessions(getMobileAppSessions(reportAsinList))
                .setMobileAppSessionsB2B(getMobileAppSessionsB2B(reportAsinList))
                .setSessions(getSessions(reportAsinList))
                .setSessionsB2B(getSessionsB2B(reportAsinList))
                .setBrowserSessionPercentage(getBrowserSessionPercentage(reportAsinList))
                .setBrowserSessionPercentageB2B(getBrowserSessionPercentageB2B(reportAsinList))
                .setMobileAppSessionPercentage(getMobileAppSessionPercentage(reportAsinList))
                .setMobileAppSessionPercentageB2B(getMobileAppSessionPercentageB2B(reportAsinList))
                .setSessionPercentage(getSessionPercentage(reportAsinList))
                .setSessionPercentageB2B(getSessionPercentageB2B(reportAsinList))
                .setBrowserPageViews(getBrowserPageViews(reportAsinList))
                .setMobileAppPageViews(getMobileAppPageViews(reportAsinList))
                .setMobileAppPageViewsB2B(getMobileAppPageViewsB2B(reportAsinList))
                .setPageViews(getPageViews(reportAsinList))
                .setPageViewsB2B(getPageViewsB2B(reportAsinList))
                .setBrowserPageViewsPercentage(getBrowserPageViewsPercentage(reportAsinList))
                .setBrowserPageViewsPercentageB2B(getBrowserPageViewsPercentageB2B(reportAsinList))
                .setMobileAppPageViewsPercentage(getMobileAppPageViewsPercentage(reportAsinList))
                .setMobileAppPageViewsPercentageB2B(
                        getMobileAppPageViewsPercentageB2B(reportAsinList))
                .setPageViewsPercentage(getPageViewsPercentage(reportAsinList))
                .setPageViewsPercentageB2B(getPageViewsPercentageB2B(reportAsinList))
                .setBuyBoxPercentage(getBuyBoxPercentage(reportAsinList))
                .setBuyBoxPercentageB2B(getBuyBoxPercentageB2B(reportAsinList))
                .setUnitSessionPercentage(getUnitSessionPercentage(reportAsinList))
                .setUnitSessionPercentageB2B(getUnitSessionPercentageB2B(reportAsinList));
    }

    private BigDecimal getUnitSessionPercentage(List<ReportAsin> reportAsinList) {
        BigDecimal amount = reportAsinList.stream()
                .map(reportAsin -> reportAsin.getTrafficByAsin().getUnitSessionPercentage())
                .mapToDouble(BigDecimal::doubleValue)
                .mapToObj(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return amount.divide(BigDecimal.valueOf(reportAsinList.size()));
    }

    private BigDecimal getBuyBoxPercentage(List<ReportAsin> reportAsinList) {
        BigDecimal amount = reportAsinList.stream()
                .map(reportAsin -> reportAsin.getTrafficByAsin().getBuyBoxPercentage())
                .mapToDouble(BigDecimal::doubleValue)
                .mapToObj(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return amount.divide(BigDecimal.valueOf(reportAsinList.size()));
    }

    private BigDecimal getPageViewsPercentage(List<ReportAsin> reportAsinList) {
        BigDecimal amount = reportAsinList.stream()
                .map(reportAsin -> reportAsin.getTrafficByAsin().getPageViewsPercentage())
                .mapToDouble(BigDecimal::doubleValue)
                .mapToObj(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return amount.divide(BigDecimal.valueOf(reportAsinList.size()));
    }

    private BigDecimal getMobileAppPageViewsPercentage(List<ReportAsin> reportAsinList) {
        BigDecimal amount = reportAsinList.stream()
                .map(reportAsin -> reportAsin.getTrafficByAsin().getMobileAppPageViewsPercentage())
                .mapToDouble(BigDecimal::doubleValue)
                .mapToObj(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return amount.divide(BigDecimal.valueOf(reportAsinList.size()));
    }

    private BigDecimal getBrowserPageViewsPercentage(List<ReportAsin> reportAsinList) {
        BigDecimal amount = reportAsinList.stream()
                .map(reportAsin -> reportAsin.getTrafficByAsin().getBrowserPageViewsPercentage())
                .mapToDouble(BigDecimal::doubleValue)
                .mapToObj(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return amount.divide(BigDecimal.valueOf(reportAsinList.size()));
    }

    private Integer getPageViews(List<ReportAsin> reportAsinList) {
        return reportAsinList.stream()
                .map(reportAsin -> reportAsin.getTrafficByAsin().getPageViews())
                .mapToInt(Integer::intValue)
                .sum();
    }

    private Integer getMobileAppPageViews(List<ReportAsin> reportAsinList) {
        return reportAsinList.stream()
                .map(reportAsin -> reportAsin.getTrafficByAsin().getMobileAppPageViews())
                .mapToInt(Integer::intValue)
                .sum();
    }

    private BigDecimal getSessionPercentage(List<ReportAsin> reportAsinList) {
        BigDecimal amount = reportAsinList.stream()
                .map(reportAsin -> reportAsin.getTrafficByAsin().getSessionPercentage())
                .mapToDouble(BigDecimal::doubleValue)
                .mapToObj(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return amount.divide(BigDecimal.valueOf(reportAsinList.size()));
    }

    private BigDecimal getMobileAppSessionPercentage(List<ReportAsin> reportAsinList) {
        BigDecimal amount = reportAsinList.stream()
                .map(reportAsin -> reportAsin.getTrafficByAsin().getMobileAppSessionPercentage())
                .mapToDouble(BigDecimal::doubleValue)
                .mapToObj(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return amount.divide(BigDecimal.valueOf(reportAsinList.size()));
    }

    private BigDecimal getBrowserSessionPercentage(List<ReportAsin> reportAsinList) {
        BigDecimal amount = reportAsinList.stream()
                .map(reportAsin -> reportAsin.getTrafficByAsin().getBrowserSessionPercentage())
                .mapToDouble(BigDecimal::doubleValue)
                .mapToObj(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return amount.divide(BigDecimal.valueOf(reportAsinList.size()));
    }

    private Integer getSessions(List<ReportAsin> reportAsinList) {
        return reportAsinList.stream()
                .map(reportAsin -> reportAsin.getTrafficByAsin().getSessions())
                .mapToInt(Integer::intValue)
                .sum();
    }

    private Integer getMobileAppSessions(List<ReportAsin> reportAsinList) {
        return reportAsinList.stream()
                .map(reportAsin -> reportAsin.getTrafficByAsin().getMobileAppSessions())
                .mapToInt(Integer::intValue)
                .sum();
    }

    private Integer getBrowserSessions(List<ReportAsin> reportAsinList) {
        return reportAsinList.stream()
                .map(reportAsin -> reportAsin.getTrafficByAsin().getBrowserSessions())
                .mapToInt(Integer::intValue)
                .sum();
    }

    private BigDecimal getBrowserPageViewsPercentageB2B(List<ReportAsin> reportAsinList) {
        BigDecimal amount = reportAsinList.stream()
                .map(reportAsin -> reportAsin.getTrafficByAsin().getBrowserPageViewsPercentageB2B())
                .mapToDouble(BigDecimal::doubleValue)
                .mapToObj(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return amount.divide(BigDecimal.valueOf(reportAsinList.size()));
    }

    private Integer getBrowserSessionsB2B(List<ReportAsin> reportAsinList) {
        return reportAsinList.stream()
                .map(reportAsin -> reportAsin.getTrafficByAsin().getBrowserSessionsB2B())
                .mapToInt(Integer::intValue)
                .sum();
    }

    private BigDecimal getBuyBoxPercentageB2B(List<ReportAsin> reportAsinList) {
        BigDecimal amount = reportAsinList.stream()
                .map(reportAsin -> reportAsin.getTrafficByAsin().getBuyBoxPercentageB2B())
                .mapToDouble(BigDecimal::doubleValue)
                .mapToObj(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return amount.divide(BigDecimal.valueOf(reportAsinList.size()));

    }

    private Integer getMobileAppPageViewsB2B(List<ReportAsin> reportAsinList) {
        return reportAsinList.stream()
                .map(reportAsin -> reportAsin.getTrafficByAsin().getMobileAppPageViewsB2B())
                .mapToInt(Integer::intValue)
                .sum();
    }

    private BigDecimal getMobileAppPageViewsPercentageB2B(List<ReportAsin> reportAsinList) {
        BigDecimal amount = reportAsinList.stream()
                .map(reportAsin -> reportAsin.getTrafficByAsin()
                        .getMobileAppPageViewsPercentageB2B())
                .mapToDouble(BigDecimal::doubleValue)
                .mapToObj(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return amount.divide(BigDecimal.valueOf(reportAsinList.size()));
    }

    private BigDecimal getMobileAppSessionPercentageB2B(List<ReportAsin> reportAsinList) {
        BigDecimal amount = reportAsinList.stream()
                .map(reportAsin -> reportAsin.getTrafficByAsin()
                        .getMobileAppSessionPercentageB2B())
                .mapToDouble(BigDecimal::doubleValue)
                .mapToObj(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return amount.divide(BigDecimal.valueOf(reportAsinList.size()));

    }

    private Integer getMobileAppSessionsB2B(List<ReportAsin> reportAsinList) {
        return reportAsinList.stream()
                .map(reportAsin -> reportAsin.getTrafficByAsin()
                        .getMobileAppSessionsB2B())
                .mapToInt(Integer::intValue)
                .sum();

    }

    private Integer getPageViewsB2B(List<ReportAsin> reportAsinList) {
        return reportAsinList.stream()
                .map(reportAsin -> reportAsin.getTrafficByAsin().getPageViewsB2B())
                .mapToInt(Integer::intValue)
                .sum();
    }

    private BigDecimal getPageViewsPercentageB2B(List<ReportAsin> reportAsinList) {
        BigDecimal amount = reportAsinList.stream()
                .map(reportAsin -> reportAsin.getTrafficByAsin().getPageViewsPercentageB2B())
                .mapToDouble(BigDecimal::doubleValue)
                .mapToObj(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return amount.divide(BigDecimal.valueOf(reportAsinList.size()));
    }

    private BigDecimal getSessionPercentageB2B(List<ReportAsin> reportAsinList) {
        BigDecimal amount = reportAsinList.stream()
                .map(reportAsin -> reportAsin.getTrafficByAsin().getSessionPercentageB2B())
                .mapToDouble(BigDecimal::doubleValue)
                .mapToObj(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return amount.divide(BigDecimal.valueOf(reportAsinList.size()));
    }

    private Integer getSessionsB2B(List<ReportAsin> reportAsinList) {
        return reportAsinList.stream()
                .map(reportAsin -> reportAsin.getTrafficByAsin().getSessionsB2B())
                .mapToInt(Integer::intValue)
                .sum();
    }

    private BigDecimal getUnitSessionPercentageB2B(List<ReportAsin> reportAsinList) {
        BigDecimal amount = reportAsinList.stream()
                .map(reportAsin -> reportAsin.getTrafficByAsin().getUnitSessionPercentageB2B())
                .mapToDouble(BigDecimal::doubleValue)
                .mapToObj(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return amount.divide(BigDecimal.valueOf(reportAsinList.size()));

    }

    private BigDecimal getBrowserSessionPercentageB2B(List<ReportAsin> reportAsinList) {
        BigDecimal amount = reportAsinList.stream()
                .map(reportAsin -> reportAsin.getTrafficByAsin().getBrowserSessionPercentageB2B())
                .mapToDouble(BigDecimal::doubleValue)
                .mapToObj(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return amount.divide(BigDecimal.valueOf(reportAsinList.size()));
    }

    private Integer getBrowserPageViewsB2B(List<ReportAsin> reportAsinList) {
        return reportAsinList.stream()
                .map(reportAsin -> reportAsin.getTrafficByAsin().getBrowserPageViewsB2B())
                .mapToInt(Integer::intValue)
                .sum();
    }

    private Integer getBrowserPageViews(List<ReportAsin> reportAsinList) {
        return reportAsinList.stream()
                .map(reportAsin -> reportAsin.getTrafficByAsin().getBrowserPageViews())
                .mapToInt(Integer::intValue)
                .sum();
    }
}
