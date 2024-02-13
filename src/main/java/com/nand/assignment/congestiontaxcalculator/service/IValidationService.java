package com.nand.assignment.congestiontaxcalculator.service;

import com.nand.assignment.congestiontaxcalculator.model.TaxCalculatorRequest;

import java.time.LocalDate;

/**
 * This will deal with all validations at one place and reduce this logic in TaxCalculatorService
 *  class.
 * Following the SOLID Principle, this responsibility is removed from main class
 * it is easy to maintain in longer time
 *
 * @author nandlalajisingh@gmail.com
 * @since 1.0
 */
public interface IValidationService {

    /**
     * Check all validations
     *
     * @param taxCalculatorRequest - request for validation
     * @return - boolean value after checking all validation
     */
    boolean validate(TaxCalculatorRequest taxCalculatorRequest);

    /**
     * Placeholder for free date check
     *
     * @param date - date to validate
     * @return - boolean value
     */
    boolean checkForTaxFreeDay(LocalDate date);
}
