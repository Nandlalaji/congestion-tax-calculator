package com.nand.assignment.congestiontaxcalculator.service.validation;

import com.nand.assignment.congestiontaxcalculator.model.TaxCalculatorRequest;

/**
 * This is used to implement many validation without changing TaxValidator class
 * Helpful in implementing chain of Responsibility Design Pattern
 *
 * @author nandlalajisingh@gmail.com
 * @since 1.0
 */
public interface IValidationRule {

    boolean check(TaxCalculatorRequest taxInputInfo);
}
