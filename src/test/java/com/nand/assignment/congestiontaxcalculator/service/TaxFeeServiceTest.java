package com.nand.assignment.congestiontaxcalculator.service;

import com.nand.assignment.congestiontaxcalculator.config.AppConfig;
import com.nand.assignment.congestiontaxcalculator.config.TaxFeeConfig;
import com.nand.assignment.congestiontaxcalculator.model.TaxFee;
import com.nand.assignment.congestiontaxcalculator.service.impl.TaxFeeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaxFeeServiceTest {

    @Mock
    private TaxFeeConfig taxFeeConfig;

    @InjectMocks
    private TaxFeeService taxFeeService;

    @Test
    @DisplayName("Get Tax Fee when time is null for a city")
    public void testGetTaxFeeWhenTimeIsNull() {

        assertEquals(0, taxFeeService.getTaxFee("Gothenburg", null));

    }

    @Test
    @DisplayName("Get Tax Fee for a given time for a city")
    public void testGetTaxFeeForValidTime() {

        TaxFee t1 = new TaxFee("06:00:00","06:29:00", 12);
        TaxFee t2 = new TaxFee("14:00:00","14:59:00", 10);
        var taxFeeList = new ArrayList<TaxFee>();
        taxFeeList.add(t1);
        taxFeeList.add(t2);

        when(taxFeeConfig.getTaxByCity("Gothenburg")).thenReturn(taxFeeList);

        assertEquals(12, taxFeeService.getTaxFee("Gothenburg", LocalTime.of(6, 15)));

    }
}
