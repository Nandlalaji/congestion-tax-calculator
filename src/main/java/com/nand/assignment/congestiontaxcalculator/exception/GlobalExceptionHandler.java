package com.nand.assignment.congestiontaxcalculator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Send proper error response
 *
 * @author nandlalajisingh@gmail.com
 * @since 1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getValidationErrors(bindingResult));
    }

    @ExceptionHandler(InvalidInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleMyValidationException(InvalidInputException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    private String getValidationErrors(BindingResult bindingResult) {
        StringBuilder errors = new StringBuilder("Validation errors: ");
        bindingResult.getFieldErrors().forEach(fieldError ->
                errors.append(fieldError.getField())
                        .append(" ")
                        .append(fieldError.getDefaultMessage())
                        .append("; "));
        return errors.toString();
    }
}
