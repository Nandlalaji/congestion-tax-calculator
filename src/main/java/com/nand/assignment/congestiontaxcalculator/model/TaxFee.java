package com.nand.assignment.congestiontaxcalculator.model;


public record TaxFee(int fromHour, int toHour, int fromMinute, int toMinute, int fee) {
}
