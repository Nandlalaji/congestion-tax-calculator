package com.nand.assignment.congestiontaxcalculator.controller;

import com.nand.assignment.congestiontaxcalculator.model.TaxCalculatorRequest;
import com.nand.assignment.congestiontaxcalculator.model.TaxCalculatorResponse;
import com.nand.assignment.congestiontaxcalculator.service.ITaxCalculatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/congestion-tax")
public class TaxCalculatorController {

    private final ITaxCalculatorService calculatorService;

    public TaxCalculatorController(ITaxCalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @PostMapping("/calculate-tax")
    public ResponseEntity<TaxCalculatorResponse> calculateTaxFee(@RequestBody TaxCalculatorRequest taxCalculatorRequest) {
        return ResponseEntity.ok(calculatorService.getTax(taxCalculatorRequest));
    }
}
