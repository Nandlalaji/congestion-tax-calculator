package com.nand.assignment.congestiontaxcalculator.model;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record TaxCalculatorRequest(String city, String vehicle,
                                   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") List<LocalDateTime> taxDateTime) {
}
