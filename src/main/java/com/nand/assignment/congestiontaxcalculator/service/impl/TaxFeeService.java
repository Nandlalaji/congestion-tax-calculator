package com.nand.assignment.congestiontaxcalculator.service.impl;

import com.nand.assignment.congestiontaxcalculator.config.AppConfig;
import com.nand.assignment.congestiontaxcalculator.config.TaxFeeConfig;
import com.nand.assignment.congestiontaxcalculator.exception.ConfigFileReadException;
import com.nand.assignment.congestiontaxcalculator.model.TaxFee;
import com.nand.assignment.congestiontaxcalculator.service.ITaxFeeService;
import com.nand.assignment.congestiontaxcalculator.utils.AppConstants;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

/**
 * This will deal with getting fee for particular time.
 * Following the SOLID Principle, this responsibility is removed from main class
 * it is easy to maintain in longer time
 *
 * @author nandlalajisingh@gmail.com
 * @since 1.0
 */
@Service
public class TaxFeeService implements ITaxFeeService {

    private final TaxFeeConfig taxFeeConfig;

    private final AppConfig appConfig;

    public TaxFeeService(TaxFeeConfig taxFeeConfig, AppConfig appConfig) {
        this.taxFeeConfig = taxFeeConfig;
        this.appConfig = appConfig;
    }

    /**
     * Get Fee for a given time
     *
     * @param time - input needed to get the tax
     * @return - tax fee for that time
     */
    @Override
    public int getTaxFee(String city, LocalTime time) {
        if (time == null) {
            return 0;
        }
        if (city == null || city.isBlank()) {
            city = appConfig.getDefaultTaxFeeCity();
        }
        var fee = getTaxFee(city).stream()
                .filter(taxFee -> checkForFeeSlot(taxFee, time))
                .findFirst();
        return fee.map(TaxFee::fee).orElse(0);
    }

    /**
     * Returns list of TaxFee with fee and fromTime to toTime
     *
     * @return - List of all fee from properties file
     */
    private List<TaxFee> getTaxFee(String city) {
        var taxFee = taxFeeConfig.getTaxByCity(city);
        if (taxFee.isEmpty()) {
            throw new ConfigFileReadException(AppConstants.EXCEPTION_DATA_NOT_AVAILABLE);
        }
        return taxFee;
    }

    /**
     * Check if provided time is inside time slot provided in config
     *
     * @return - boolean value after checking if provided time is under given time slot
     */
    private boolean checkForFeeSlot(TaxFee taxFee, LocalTime time) {
        LocalTime fromTime, toTime;
        try {
            fromTime = LocalTime.parse(taxFee.fromTime());
            toTime = LocalTime.parse(taxFee.toTime());
        } catch (Exception ex) {
            throw new ConfigFileReadException("Date format in tax-fee-config is not correct - "
                    + ex.getMessage());
        }

        return time.equals(fromTime) ||
                time.equals(toTime) ||
                time.isAfter(fromTime) && time.isBefore(toTime);

    }
}
