package com.nand.assignment.congestiontaxcalculator.model;

import java.util.List;

public record TaxFreeDate(int year, int month, List<Integer> days) {
}
