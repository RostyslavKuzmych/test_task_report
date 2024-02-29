package application.service.impl;

import application.exception.EntityNotFoundException;
import application.model.OrderedProductSales;
import application.model.OrderedProductSalesB2B;
import application.model.Report;
import application.model.ReportDate;
import application.model.SalesByDate;
import application.model.TrafficByDate;
import application.repository.ReportDateRepository;
import application.service.JsonReaderService;
import application.service.ReportDateService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportDateServiceImpl implements ReportDateService {
    private static final String FIND_EXCEPTION = "Can't find report by date ";
    private final JsonReaderService jsonReaderService;
    private final ReportDateRepository reportDateRepository;

    @Override
    @Cacheable(value = "allResponses", unless = "#result == null")
    public ReportDate getReportByDate(LocalDate localDate) {
        return reportDateRepository.findByDate(localDate)
                .orElseThrow(() -> new EntityNotFoundException(FIND_EXCEPTION + localDate));
    }

    @Override
    public ReportDate getReportByAllDates() {
        Report report = jsonReaderService.readFile();
        return new ReportDate().setSalesByDate(getSalesByDate(report.getSalesAndTrafficByDate()))
                .setTrafficByDate(getTrafficByDate(report.getSalesAndTrafficByDate()));
    }

    private SalesByDate getSalesByDate(List<ReportDate> reportDateList) {
        return new SalesByDate()
                .setUnitsOrdered(getUnitsOrdered(reportDateList))
                .setUnitsOrderedB2B(getUnitsOrderedB2B(reportDateList))
                .setOrderedProductSales(getOrderedProductSales(reportDateList))
                .setOrderedProductSalesB2B(getOrderedProductSalesB2B(reportDateList))
                .setTotalOrderItems(getTotalOrderItems(reportDateList))
                .setTotalOrderItemsB2B(getTotalOrderItemsB2B(reportDateList));
    }

    private Integer getTotalOrderItemsB2B(List<ReportDate> reportDateList) {
        return reportDateList.stream()
                .map(reportDate -> reportDate.getSalesByDate().getTotalOrderItemsB2B())
                .mapToInt(Integer::intValue)
                .sum();
    }

    private Integer getTotalOrderItems(List<ReportDate> reportDateList) {
        return reportDateList.stream()
                .map(reportDate -> reportDate.getSalesByDate().getTotalOrderItems())
                .mapToInt(Integer::intValue)
                .sum();
    }

    private OrderedProductSalesB2B getOrderedProductSalesB2B(List<ReportDate> reportDateList) {
        BigDecimal amount = reportDateList.stream()
                .map(reportAsin -> reportAsin.getSalesByDate()
                        .getOrderedProductSalesB2B().getAmount())
                .mapToDouble(BigDecimal::doubleValue)
                .mapToObj(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        String currencyCode = reportDateList.stream().map(reportAsin
                        -> reportAsin.getSalesByDate()
                        .getOrderedProductSalesB2B().getCurrencyCode())
                .findFirst().get();
        return new OrderedProductSalesB2B().setCurrencyCode(currencyCode).setAmount(amount);
    }

    private OrderedProductSales getOrderedProductSales(List<ReportDate> reportDateList) {
        BigDecimal amount = reportDateList.stream()
                .map(reportAsin -> reportAsin.getSalesByDate()
                        .getOrderedProductSales().getAmount())
                .mapToDouble(BigDecimal::doubleValue)
                .mapToObj(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        String currencyCode = reportDateList.stream().map(reportAsin
                        -> reportAsin.getSalesByDate().getOrderedProductSales().getCurrencyCode())
                .findFirst().get();
        return new OrderedProductSales().setCurrencyCode(currencyCode).setAmount(amount);
    }

    private Integer getUnitsOrderedB2B(List<ReportDate> reportDateList) {
        return reportDateList.stream()
                .map(reportDate -> reportDate.getSalesByDate().getUnitsOrderedB2B())
                .mapToInt(Integer::intValue)
                .sum();
    }

    private Integer getUnitsOrdered(List<ReportDate> reportDateList) {
        return reportDateList.stream()
                .map(reportDate -> reportDate.getSalesByDate().getUnitsOrdered())
                .mapToInt(Integer::intValue)
                .sum();
    }

    private TrafficByDate getTrafficByDate(List<ReportDate> reportDateList) {
        return new TrafficByDate()
                .setBrowserPageViews(getBrowserPageViews(reportDateList))
                .setBrowserPageViewsB2B(getBrowserPageViewsB2B(reportDateList))
                .setMobileAppPageViews(getMobileAppPageViews(reportDateList))
                .setMobileAppPageViewsB2B(getMobileAppPageViewsB2B(reportDateList))
                .setPageViews(getPageViews(reportDateList))
                .setPageViewsB2B(getPageViewsB2B(reportDateList))
                .setBrowserSessions(getBrowserSessions(reportDateList))
                .setBrowserSessionsB2B(getBrowserSessionsB2B(reportDateList))
                .setMobileAppSessions(getMobileAppSessions(reportDateList))
                .setMobileAppSessionsB2B(getMobileAppSessionsB2B(reportDateList))
                .setSessions(getSessions(reportDateList))
                .setSessionsB2B(getSessionsB2B(reportDateList))
                .setBuyBoxPercentage(getBuyBoxPercentage(reportDateList))
                .setBuyBoxPercentageB2B(getBuyBoxPercentageB2B(reportDateList))
                .setOrderItemSessionPercentage(getOrderItemSessionPercentage(reportDateList))
                .setOrderItemSessionPercentageB2B(getOrderItemSessionPercentageB2B(reportDateList))
                .setUnitSessionPercentage(getUnitSessionPercentage(reportDateList))
                .setUnitSessionPercentageB2B(getUnitSessionPercentageB2B(reportDateList))
                .setAverageOfferCount(getAverageOfferCount(reportDateList))
                .setAverageParentItems(getAverageParentItems(reportDateList))
                .setFeedbackReceived(getFeedbackReceived(reportDateList))
                .setNegativeFeedbackReceived(getNegativeFeedbackReceived(reportDateList))
                .setReceivedNegativeFeedbackRate(getReceivedNegativeFeedbackRate(reportDateList));
    }

    private Integer getReceivedNegativeFeedbackRate(List<ReportDate> reportDateList) {
        return reportDateList.stream()
                .map(reportDate -> reportDate.getTrafficByDate().getReceivedNegativeFeedbackRate())
                .mapToInt(Integer::intValue)
                .sum();
    }

    private Integer getNegativeFeedbackReceived(List<ReportDate> reportDateList) {
        return reportDateList.stream()
                .map(reportDate -> reportDate.getTrafficByDate().getNegativeFeedbackReceived())
                .mapToInt(Integer::intValue)
                .sum();
    }

    private Integer getFeedbackReceived(List<ReportDate> reportDateList) {
        return reportDateList.stream()
                .map(reportDate -> reportDate.getTrafficByDate().getFeedbackReceived())
                .mapToInt(Integer::intValue)
                .sum();
    }

    private Integer getAverageParentItems(List<ReportDate> reportDateList) {
        Integer allParentItems = reportDateList.stream()
                .map(reportDate -> reportDate.getTrafficByDate().getAverageParentItems())
                .mapToInt(Integer::intValue)
                .sum();
        return allParentItems / reportDateList.size();
    }

    private Integer getAverageOfferCount(List<ReportDate> reportDateList) {
        Integer allOffersCount = reportDateList.stream()
                .map(reportDate -> reportDate.getTrafficByDate().getAverageOfferCount())
                .mapToInt(Integer::intValue)
                .sum();
        return allOffersCount / reportDateList.size();
    }

    private BigDecimal getUnitSessionPercentageB2B(List<ReportDate> reportDateList) {
        BigDecimal amount = reportDateList.stream()
                .map(reportAsin -> reportAsin.getTrafficByDate().getUnitSessionPercentageB2B())
                .mapToDouble(BigDecimal::doubleValue)
                .mapToObj(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return amount.divide(BigDecimal.valueOf(reportDateList.size()));
    }

    private BigDecimal getUnitSessionPercentage(List<ReportDate> reportDateList) {
        BigDecimal amount = reportDateList.stream()
                .map(reportAsin -> reportAsin.getTrafficByDate().getUnitSessionPercentage())
                .mapToDouble(BigDecimal::doubleValue)
                .mapToObj(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return amount.divide(BigDecimal.valueOf(reportDateList.size()));
    }

    private BigDecimal getOrderItemSessionPercentageB2B(List<ReportDate> reportDateList) {
        BigDecimal amount = reportDateList.stream()
                .map(reportAsin -> reportAsin.getTrafficByDate().getOrderItemSessionPercentageB2B())
                .mapToDouble(BigDecimal::doubleValue)
                .mapToObj(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return amount.divide(BigDecimal.valueOf(reportDateList.size()));
    }

    private BigDecimal getOrderItemSessionPercentage(List<ReportDate> reportDateList) {
        BigDecimal amount = reportDateList.stream()
                .map(reportAsin -> reportAsin.getTrafficByDate().getOrderItemSessionPercentage())
                .mapToDouble(BigDecimal::doubleValue)
                .mapToObj(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return amount.divide(BigDecimal.valueOf(reportDateList.size()));
    }

    private BigDecimal getBuyBoxPercentageB2B(List<ReportDate> reportDateList) {
        BigDecimal amount = reportDateList.stream()
                .map(reportAsin -> reportAsin.getTrafficByDate().getBuyBoxPercentageB2B())
                .mapToDouble(BigDecimal::doubleValue)
                .mapToObj(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return amount.divide(BigDecimal.valueOf(reportDateList.size()));
    }

    private BigDecimal getBuyBoxPercentage(List<ReportDate> reportDateList) {
        BigDecimal amount = reportDateList.stream()
                .map(reportAsin -> reportAsin.getTrafficByDate().getBuyBoxPercentage())
                .mapToDouble(BigDecimal::doubleValue)
                .mapToObj(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return amount.divide(BigDecimal.valueOf(reportDateList.size()));
    }

    private Integer getSessionsB2B(List<ReportDate> reportDateList) {
        Integer allOffersCount = reportDateList.stream()
                .map(reportDate -> reportDate.getTrafficByDate().getSessionsB2B())
                .mapToInt(Integer::intValue)
                .sum();
        return allOffersCount / reportDateList.size();
    }

    private Integer getSessions(List<ReportDate> reportDateList) {
        Integer allOffersCount = reportDateList.stream()
                .map(reportDate -> reportDate.getTrafficByDate().getSessions())
                .mapToInt(Integer::intValue)
                .sum();
        return allOffersCount / reportDateList.size();
    }

    private Integer getMobileAppSessionsB2B(List<ReportDate> reportDateList) {
        Integer allOffersCount = reportDateList.stream()
                .map(reportDate -> reportDate.getTrafficByDate().getMobileAppSessionsB2B())
                .mapToInt(Integer::intValue)
                .sum();
        return allOffersCount / reportDateList.size();
    }

    private Integer getMobileAppSessions(List<ReportDate> reportDateList) {
        Integer allOffersCount = reportDateList.stream()
                .map(reportDate -> reportDate.getTrafficByDate().getMobileAppSessions())
                .mapToInt(Integer::intValue)
                .sum();
        return allOffersCount / reportDateList.size();
    }

    private Integer getBrowserSessionsB2B(List<ReportDate> reportDateList) {
        Integer allOffersCount = reportDateList.stream()
                .map(reportDate -> reportDate.getTrafficByDate().getBrowserSessionsB2B())
                .mapToInt(Integer::intValue)
                .sum();
        return allOffersCount / reportDateList.size();
    }

    private Integer getBrowserSessions(List<ReportDate> reportDateList) {
        Integer allOffersCount = reportDateList.stream()
                .map(reportDate -> reportDate.getTrafficByDate().getBrowserSessions())
                .mapToInt(Integer::intValue)
                .sum();
        return allOffersCount / reportDateList.size();
    }

    private Integer getPageViewsB2B(List<ReportDate> reportDateList) {
        Integer allOffersCount = reportDateList.stream()
                .map(reportDate -> reportDate.getTrafficByDate().getPageViewsB2B())
                .mapToInt(Integer::intValue)
                .sum();
        return allOffersCount / reportDateList.size();
    }

    private Integer getPageViews(List<ReportDate> reportDateList) {
        Integer allOffersCount = reportDateList.stream()
                .map(reportDate -> reportDate.getTrafficByDate().getPageViews())
                .mapToInt(Integer::intValue)
                .sum();
        return allOffersCount / reportDateList.size();
    }

    private Integer getMobileAppPageViewsB2B(List<ReportDate> reportDateList) {
        Integer allOffersCount = reportDateList.stream()
                .map(reportDate -> reportDate.getTrafficByDate().getMobileAppPageViewsB2B())
                .mapToInt(Integer::intValue)
                .sum();
        return allOffersCount / reportDateList.size();
    }

    private Integer getMobileAppPageViews(List<ReportDate> reportDateList) {
        Integer allOffersCount = reportDateList.stream()
                .map(reportDate -> reportDate.getTrafficByDate().getMobileAppPageViews())
                .mapToInt(Integer::intValue)
                .sum();
        return allOffersCount / reportDateList.size();
    }

    private Integer getBrowserPageViewsB2B(List<ReportDate> reportDateList) {
        Integer allOffersCount = reportDateList.stream()
                .map(reportDate -> reportDate.getTrafficByDate().getBrowserPageViewsB2B())
                .mapToInt(Integer::intValue)
                .sum();
        return allOffersCount / reportDateList.size();
    }

    private Integer getBrowserPageViews(List<ReportDate> reportDateList) {
        Integer allOffersCount = reportDateList.stream()
                .map(reportDate -> reportDate.getTrafficByDate().getBrowserPageViews())
                .mapToInt(Integer::intValue)
                .sum();
        return allOffersCount / reportDateList.size();
    }
}
