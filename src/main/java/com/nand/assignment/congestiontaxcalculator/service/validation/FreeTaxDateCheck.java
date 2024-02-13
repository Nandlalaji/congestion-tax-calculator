package com.nand.assignment.congestiontaxcalculator.service.validation;

import com.nand.assignment.congestiontaxcalculator.config.AppConfig;
import com.nand.assignment.congestiontaxcalculator.config.TaxFreeConfig;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class deals with checking if provided tax date comes under holiday or weekends
 * if it comes under that days, it will be tax-free day.
 *
 * @author nandlalajisingh@gmail.com
 * @since 1.0
 */
@Service
public class FreeTaxDateCheck {

    private final AppConfig appConfig;

    private final TaxFreeConfig taxFreeConfig;

    private Set<LocalDate> taxFreeDates;

    public FreeTaxDateCheck(AppConfig appConfig, TaxFreeConfig taxFreeConfig) {
        this.appConfig = appConfig;
        this.taxFreeConfig = taxFreeConfig;
        initTaxFreeDates();
    }

    /**
     * Checks if input date is  holiday or weekend
     *
     * @param date input date
     * @return boolean value
     */
    public boolean checkForTaxFreeDay(LocalDate date) {
        if (date == null) {
            return false;
        }
        if (!appConfig.getAllowYear().equals(date.getYear())) {
            return false;
        }
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY || taxFreeDates.contains(date);

    }

    /**
     * Convert List of TaxFreeDates to LocalDate to get only dates
     */
    private void initTaxFreeDates() {
        taxFreeDates = taxFreeConfig.getDates()
                .stream()
                .map(taxFreeDate -> taxFreeDate
                        .days()
                        .stream()
                        .map(day -> LocalDate.of(taxFreeDate.year(), taxFreeDate.month(), day))
                        .collect(Collectors.toSet()))
                .collect(Collectors.toSet()).stream().flatMap(Collection::stream).collect(Collectors.toSet());
    }

}
