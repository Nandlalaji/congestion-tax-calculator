package com.nand.assignment.congestiontaxcalculator.service.impl;

import com.nand.assignment.congestiontaxcalculator.config.AppConfig;
import com.nand.assignment.congestiontaxcalculator.model.TaxCalculatorRequest;
import com.nand.assignment.congestiontaxcalculator.model.TaxCalculatorResponse;
import com.nand.assignment.congestiontaxcalculator.service.ITaxCalculatorService;
import com.nand.assignment.congestiontaxcalculator.service.ITaxFeeService;
import com.nand.assignment.congestiontaxcalculator.service.IValidationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.nand.assignment.congestiontaxcalculator.utils.AppConstants.TIME_INTERVAL;

/**
 * This will deal with getting  tax amount for a vehicle with list of tax times.
 * It follows SOLID principle where one responsibility is given to one class.
 * With validator is follows open-close principle. Easy to extend by adding more validation class
 * All the methods are defined in implementation which indicate Liskov Substitution and Interface Segregation
 * It also has Dependency inversion by providing creation of separately and pass as argument of constructor
 * ---
 * Also chain of responsibility DESIGN PATTERN is used for validation.
 *
 * @author nandlalajisingh@gmail.com
 * @since 1.0
 */
@Service
public class TaxCalculatorService implements ITaxCalculatorService {

    private final ITaxFeeService taxFeeService;

    private final IValidationService validationService;

    private final AppConfig appConfig;


    public TaxCalculatorService(ITaxFeeService taxFeeService, IValidationService validationService,
                                AppConfig appConfig) {
        this.taxFeeService = taxFeeService;
        this.validationService = validationService;
        this.appConfig = appConfig;
    }

    /**
     * Calculate the total tax fee. This does the validation and sort date.
     * it also make map of dates to do calculation dayWise
     *
     * @param taxCalculatorRequest - input needed to calculate the tax
     * @return - the total tax fee for that day
     */
    @Override
    public TaxCalculatorResponse getTax(TaxCalculatorRequest taxCalculatorRequest) {

        if (validationService.validate(taxCalculatorRequest)) {
            return new TaxCalculatorResponse(0); // tax-free vehicle
        }

        var taxDateTimeList = taxCalculatorRequest.taxDateTime();
        Collections.sort(taxDateTimeList);

        // multiple dates and time slots
        var dateToDateTimeMap = taxDateTimeList.stream()
                .collect(Collectors.groupingBy(LocalDateTime::toLocalDate));

        return new TaxCalculatorResponse(getTaxFee(taxCalculatorRequest.city().toLowerCase(), dateToDateTimeMap));

    }

    /**
     * Calculate the total tax fee day wise
     *
     * @param dates - input needed to calculate the tax
     * @return - the total tax fee
     */
    private int getTaxFee(String city, Map<LocalDate, List<LocalDateTime>> dates) {
        var totalFee = 0;
        for (var entry : dates.entrySet()) {
            if (validationService.checkForTaxFreeDay(entry.getKey()))
                continue;
            var validTimeList = getValidFeeTimeList(entry.getValue());
            if (validTimeList.isEmpty()) {
                continue;
            }

            totalFee += calculateFee(city, validTimeList);
        }
        return totalFee;
    }

    /**
     * Converts the LocalDateTime to only time
     *
     * @param dates - input needed for conversion
     * @return - List of LocalTime after conversion.
     */
    private List<LocalTime> getValidFeeTimeList(List<LocalDateTime> dates) {
        return dates.stream()
                .map(LocalDateTime::toLocalTime)
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * Calculates the fee. It also takes care if many time tax are within an hour.
     * it only charges the max one.
     * it also takes care that max fee should not go more than given charge.
     *
     * @param timeList - input needed for calculation.
     * @return - List of LocalTime after conversion.
     */
    private int calculateFee(String city, List<LocalTime> timeList) {
        var intervalStart = timeList.get(0);
        int totalFee = 0;
        var intervalFees = new HashSet<Integer>();
        for (var date : timeList) {
            if (ChronoUnit.SECONDS.between(intervalStart, date) > TIME_INTERVAL) {
                intervalStart = date;
                totalFee += Collections.max(intervalFees);
                intervalFees.clear();
            }
            intervalFees.add(taxFeeService.getTaxFee(city, date));
        }
        totalFee += Collections.max(intervalFees);
        return Math.min(totalFee, appConfig.getMaxFeePerDay());
    }

}
