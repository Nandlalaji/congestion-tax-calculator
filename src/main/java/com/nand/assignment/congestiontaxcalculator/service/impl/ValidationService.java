package com.nand.assignment.congestiontaxcalculator.service.impl;

import com.nand.assignment.congestiontaxcalculator.model.TaxCalculatorRequest;
import com.nand.assignment.congestiontaxcalculator.service.IValidationService;
import com.nand.assignment.congestiontaxcalculator.service.validation.FreeTaxDateCheck;
import com.nand.assignment.congestiontaxcalculator.service.validation.FreeTaxVehicleCheck;
import com.nand.assignment.congestiontaxcalculator.service.validation.TaxInputValidator;
import com.nand.assignment.congestiontaxcalculator.service.validation.TaxValidator;
import org.springframework.stereotype.Service;

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
@Service
public class ValidationService implements IValidationService {

    private final TaxValidator taxValidator;

    private final TaxInputValidator taxInputValidator;

    private final FreeTaxVehicleCheck freeTaxVehicleCheck;

    private final FreeTaxDateCheck freeTaxDateCheck;

    public ValidationService(TaxValidator taxValidator, TaxInputValidator taxInputValidator, FreeTaxVehicleCheck freeTaxVehicleCheck, FreeTaxDateCheck freeTaxDateCheck) {
        this.taxValidator = taxValidator;
        this.taxInputValidator = taxInputValidator;
        this.freeTaxVehicleCheck = freeTaxVehicleCheck;
        this.freeTaxDateCheck = freeTaxDateCheck;
    }

    /**
     * Check all validations
     *
     * @return - boolean value after checking all validation
     */
    @Override
    public boolean validate(TaxCalculatorRequest taxCalculatorRequest) {
        // validate input and free tax
        taxValidator.registerRule(taxInputValidator);
        taxValidator.registerRule(freeTaxVehicleCheck);
        return taxValidator.validate(taxCalculatorRequest);
    }

    /**
     * Placeholder for free date check
     *
     * @return - boolean value
     */
    @Override
    public boolean checkForTaxFreeDay(LocalDate date) {
        return freeTaxDateCheck.checkForTaxFreeDay(date);
    }
}
