package com.nand.assignment.congestiontaxcalculator.service;

import java.time.LocalTime;

/**
 * This will deal with getting fee for particular time.
 * Following the SOLID Principle, this responsibility is removed from main class
 * it is easy to maintain in longer time
 *
 * @author
 * @since 1.0
 */
public interface ITaxFeeService {

    /**
     * Get Fee for a given time
     *
     * @param time - input needed to get the tax
     * @return - tax fee for that time
     */
    int getTaxFee(String city, LocalTime time);

}
