package com.nand.assignment.congestiontaxcalculator.service;

import com.nand.assignment.congestiontaxcalculator.config.AppConfig;
import com.nand.assignment.congestiontaxcalculator.exception.InvalidInputException;
import com.nand.assignment.congestiontaxcalculator.model.TaxCalculatorRequest;
import com.nand.assignment.congestiontaxcalculator.service.impl.TaxCalculatorService;
import com.nand.assignment.congestiontaxcalculator.utils.AppConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaxCalculatorServiceTest {

    @Mock
    private ITaxFeeService taxFeeService;

    @Mock
    private AppConfig appConfig;

    @Mock
   private IValidationService validationService;


    @InjectMocks
    private TaxCalculatorService taxCalculatorService;


    @Test
    @DisplayName("Null Vehicle Throws Exception")
    public void testGetTaxWithNullVehicleThrowsException() {
        TaxCalculatorRequest taxCalculatorRequest = new TaxCalculatorRequest("Gothenburg", null, null);
        doThrow(new InvalidInputException(AppConstants.EXCEPTION_INVALID_VEHICLE)).when(validationService).validate(taxCalculatorRequest);
        InvalidInputException exception = assertThrows(InvalidInputException.class,
                () -> taxCalculatorService.getTax(taxCalculatorRequest));
        assertEquals(AppConstants.EXCEPTION_INVALID_VEHICLE, exception.getMessage());
    }

    @Test
    @DisplayName("Free Vehicle has zero fee")
    public void testGetTaxWithFreeVehicle() {
        TaxCalculatorRequest taxCalculatorRequest = new TaxCalculatorRequest("Gothenburg", "Car", null);
        when(validationService.validate(taxCalculatorRequest)).thenReturn(Boolean.TRUE);
        assertEquals(0, taxCalculatorService.getTax(taxCalculatorRequest).fee());
    }

    @Test
    @DisplayName("Vehicle with one day holiday has zero fee")
    public void testGetTaxWithOneDayHoliday() {
        var testLocalDateTime = LocalDateTime.of(2013, 5, 5, 12, 0, 15);
        var taxDateTime = new ArrayList<LocalDateTime>();
        taxDateTime.add(testLocalDateTime);
        TaxCalculatorRequest taxCalculatorRequest = new TaxCalculatorRequest("Gothenburg", "Car", taxDateTime);
        when(validationService.validate(taxCalculatorRequest)).thenReturn(Boolean.FALSE);
        when(validationService.checkForTaxFreeDay(testLocalDateTime.toLocalDate())).thenReturn(Boolean.TRUE);
        assertEquals(0, taxCalculatorService.getTax(taxCalculatorRequest).fee());
    }

    @Test
    @DisplayName("Vehicle with one day holiday and another non holiday has some fee")
    public void testGetTaxWithOneDayHolidayAndOtherNonHoliday() {
        var holidayLocalDateTime = LocalDateTime.of(2013, 5, 5, 12, 0, 15);
        var nonHolidayLocalDateTime = LocalDateTime.of(2013, 7, 5, 12, 0, 15);

        var taxDateTime = new ArrayList<LocalDateTime>();
        taxDateTime.add(holidayLocalDateTime);
        taxDateTime.add(nonHolidayLocalDateTime);
        TaxCalculatorRequest taxCalculatorRequest = new TaxCalculatorRequest("Gothenburg", "Car", taxDateTime);
        when(validationService.validate(taxCalculatorRequest)).thenReturn(Boolean.FALSE);
        when(validationService.checkForTaxFreeDay(holidayLocalDateTime.toLocalDate())).thenReturn(Boolean.TRUE);
        when(validationService.checkForTaxFreeDay(nonHolidayLocalDateTime.toLocalDate())).thenReturn(Boolean.FALSE);
        when(taxFeeService.getTaxFee("gothenburg", nonHolidayLocalDateTime.toLocalTime())).thenReturn(10);
        when(appConfig.getMaxFeePerDay()).thenReturn(50);
        assertEquals(10, taxCalculatorService.getTax(taxCalculatorRequest).fee());
    }

    @Test
    @DisplayName("Vehicle with only non holiday has some fee")
    public void testGetTaxWithOnlyNonHoliday() {
        var nonHolidayLocalDateTime = LocalDateTime.of(2013, 7, 5, 12, 0, 15);

        var taxDateTime = new ArrayList<LocalDateTime>();
        taxDateTime.add(nonHolidayLocalDateTime);
        TaxCalculatorRequest taxCalculatorRequest = new TaxCalculatorRequest("Gothenburg", "Car", taxDateTime);
        when(validationService.validate(taxCalculatorRequest)).thenReturn(Boolean.FALSE);
        when(validationService.checkForTaxFreeDay(nonHolidayLocalDateTime.toLocalDate())).thenReturn(Boolean.FALSE);
        when(taxFeeService.getTaxFee("gothenburg", nonHolidayLocalDateTime.toLocalTime())).thenReturn(10);
        when(appConfig.getMaxFeePerDay()).thenReturn(50);
        assertEquals(10, taxCalculatorService.getTax(taxCalculatorRequest).fee());
    }

    @Test
    @DisplayName("Vehicle with two non holiday with different fee time has some fee")
    public void testGetTaxWithTwoNonHoliday() {
        var nonHolidayLocalDateTime1 = LocalDateTime.of(2013, 7, 5, 12, 0, 15);
        var nonHolidayLocalDateTime2 = LocalDateTime.of(2013, 7, 5, 15, 0, 15);

        var taxDateTime = new ArrayList<LocalDateTime>();
        taxDateTime.add(nonHolidayLocalDateTime1);
        taxDateTime.add(nonHolidayLocalDateTime2);
        TaxCalculatorRequest taxCalculatorRequest = new TaxCalculatorRequest("Gothenburg", "Car", taxDateTime);
        when(validationService.validate(taxCalculatorRequest)).thenReturn(Boolean.FALSE);
        when(validationService.checkForTaxFreeDay(nonHolidayLocalDateTime1.toLocalDate())).thenReturn(Boolean.FALSE);
        when(validationService.checkForTaxFreeDay(nonHolidayLocalDateTime2.toLocalDate())).thenReturn(Boolean.FALSE);
        when(taxFeeService.getTaxFee("gothenburg", nonHolidayLocalDateTime1.toLocalTime())).thenReturn(10);
        when(taxFeeService.getTaxFee("gothenburg", nonHolidayLocalDateTime2.toLocalTime())).thenReturn(20);
        when(appConfig.getMaxFeePerDay()).thenReturn(50);
        assertEquals(30, taxCalculatorService.getTax(taxCalculatorRequest).fee());
    }

    @Test
    @DisplayName("Vehicle will not go more than max fee")
    public void testGetTaxWithMaxValue() {
        var nonHolidayLocalDateTime1 = LocalDateTime.of(2013, 7, 5, 12, 0, 15);
        var nonHolidayLocalDateTime2 = LocalDateTime.of(2013, 7, 5, 15, 0, 15);

        var taxDateTime = new ArrayList<LocalDateTime>();
        taxDateTime.add(nonHolidayLocalDateTime1);
        taxDateTime.add(nonHolidayLocalDateTime2);
        TaxCalculatorRequest taxCalculatorRequest = new TaxCalculatorRequest("Gothenburg", "Car", taxDateTime);
        when(validationService.validate(taxCalculatorRequest)).thenReturn(Boolean.FALSE);
        when(validationService.checkForTaxFreeDay(nonHolidayLocalDateTime1.toLocalDate())).thenReturn(Boolean.FALSE);
        when(validationService.checkForTaxFreeDay(nonHolidayLocalDateTime2.toLocalDate())).thenReturn(Boolean.FALSE);
        when(taxFeeService.getTaxFee("gothenburg", nonHolidayLocalDateTime1.toLocalTime())).thenReturn(10);
        when(taxFeeService.getTaxFee("gothenburg", nonHolidayLocalDateTime2.toLocalTime())).thenReturn(20);
        when(appConfig.getMaxFeePerDay()).thenReturn(20);
        assertEquals(20, taxCalculatorService.getTax(taxCalculatorRequest).fee());
    }
}
