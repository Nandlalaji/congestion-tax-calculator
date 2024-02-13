package com.nand.assignment.congestiontaxcalculator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom Exception for input related issue
 *
 * @author nandlalajisingh@gmail.com
 * @since 1.0
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String exception) {
        super(exception);
    }

}
