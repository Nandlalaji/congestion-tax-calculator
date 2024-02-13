package com.nand.assignment.congestiontaxcalculator.exception;

/**
 * Custom Exception for config related issue
 *
 * @author nandlalajisingh@gmail.com
 * @since 1.0
 */
public class ConfigFileReadException extends RuntimeException {

    public ConfigFileReadException(String exception) {
        super(exception);
    }

}
