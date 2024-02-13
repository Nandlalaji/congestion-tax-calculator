package com.nand.assignment.congestiontaxcalculator.service;

import com.nand.assignment.congestiontaxcalculator.model.TaxCalculatorRequest;
import com.nand.assignment.congestiontaxcalculator.model.TaxCalculatorResponse;

/**
 * This will deal with getting tax amount for a vehicle with list of tax times
 *
 * @author nandlalajisingh@gmail.com
 * @since 1.0
 */
public interface ITaxCalculatorService {

    /**
     * Calculate the total tax fee
     *
     * @param taxCalculatorInput - input needed to calculate the tax
     * @return - the total tax fee for that day
     */
    TaxCalculatorResponse getTax(TaxCalculatorRequest taxCalculatorInput);

}

