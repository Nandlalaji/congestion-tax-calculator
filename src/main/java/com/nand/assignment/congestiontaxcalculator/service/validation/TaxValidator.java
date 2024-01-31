package com.nand.assignment.congestiontaxcalculator.service.validation;

import com.nand.assignment.congestiontaxcalculator.model.TaxCalculatorRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * This class deals with iterating the all validation register in main class.
 * It is example of Chain of responsibility Design pattern where add new validation by
 * implementing this and just add into registerRule
 *
 * @author
 * @since 1.0
 */
@Service
public class TaxValidator {

    private final List<IValidationRule> rules = new ArrayList<>();

    public void registerRule(IValidationRule rule) {
        this.rules.add(rule);
    }

    /**
     * Iterates the rules
     *
     * @param validationInfo input for validation
     * @return boolean
     */
    public boolean validate(TaxCalculatorRequest validationInfo) {
        var isValid = false;
        for (IValidationRule rule : rules) {
            isValid = rule.check(validationInfo);
            if (isValid) break;
        }
        return isValid;
    }

}
