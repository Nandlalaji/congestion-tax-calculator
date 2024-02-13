package com.nand.assignment.congestiontaxcalculator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nand.assignment.congestiontaxcalculator.model.TaxCalculatorRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

@SpringBootTest
@AutoConfigureMockMvc
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class})
@ActiveProfiles("test")
public class TaxCalculatorControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("Null Vehicle")
    public void testCalculateTaxFeeForNullVehicle() throws Exception {
        var taxDateTime = new ArrayList<LocalDateTime>();

        TaxCalculatorRequest request = new TaxCalculatorRequest("Gothenburg", null, taxDateTime);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/congestion-tax/calculate-tax")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Vehicle in parameter is null or empty"));
    }

    @Test
    @DisplayName("Invalid Vehicle")
    public void testCalculateTaxFeeForInvalidVehicle() throws Exception {

        var holidayLocalDateTime = LocalDateTime.of(2013, 5, 5, 12, 0, 15);
        var nonHolidayLocalDateTime = LocalDateTime.of(2013, 7, 5, 12, 0, 15);

        var taxDateTime = new ArrayList<LocalDateTime>();
        taxDateTime.add(holidayLocalDateTime);
        taxDateTime.add(nonHolidayLocalDateTime);

        TaxCalculatorRequest request = new TaxCalculatorRequest("Gothenburg", "Aeroplane", taxDateTime);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/congestion-tax/calculate-tax")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Vehicle in parameter is invalid"));
    }

    @Test
    @DisplayName("Null Date")
    public void testCalculateTaxFeeForNullDate() throws Exception {
        var taxDateTime = new ArrayList<LocalDateTime>();

        TaxCalculatorRequest request = new TaxCalculatorRequest("Gothenburg", "Car", taxDateTime);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/congestion-tax/calculate-tax")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Date time in parameter is null"));
    }

    @Test
    @DisplayName("Tax for month of July")
    public void testCalculateTaxFeeForJuly() throws Exception {

        var holidayLocalDateTime = LocalDateTime.of(2013, 5, 5, 12, 0, 15);
        var nonHolidayLocalDateTime = LocalDateTime.of(2013, 7, 5, 12, 0, 15);

        var taxDateTime = new ArrayList<LocalDateTime>();
        taxDateTime.add(holidayLocalDateTime);
        taxDateTime.add(nonHolidayLocalDateTime);

        TaxCalculatorRequest request = new TaxCalculatorRequest("Gothenburg", "Car", taxDateTime);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/congestion-tax/calculate-tax")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fee").value(0));
    }

    @Test
    @DisplayName("Tax for Single Date And Multiple times")
    public void testCalculateTaxFeeForSingleDateAndMultipleTimes() throws Exception {

        TaxCalculatorRequest request = new TaxCalculatorRequest("Gothenburg", "Car",
                Arrays.asList(LocalDateTime.of(2013, 8, 07, 06, 0),
                        LocalDateTime.of(2013, 8, 07, 14, 15)));
        System.out.println(objectMapper.writeValueAsString(request));
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/congestion-tax/calculate-tax")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fee").value(16));
    }

    @Test
    @DisplayName("Tax for Multiple Date And Multiple times")
    public void testCalculateTaxFeeForMultipleAndMultipleTimes() throws Exception {

        TaxCalculatorRequest request = new TaxCalculatorRequest("Gothenburg", "Car",
                Arrays.asList(
                        LocalDateTime.of(2013, 8, 7, 06, 0),
                        LocalDateTime.of(2013, 8, 7, 14, 15),
                        LocalDateTime.of(2013, 4, 8, 06, 0),
                        LocalDateTime.of(2013, 4, 8, 14, 15)
                ));

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/congestion-tax/calculate-tax")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fee").value(32));
    }

    @Test
    @DisplayName("Tax for Multiple times within hour")
    public void testCalculateTaxFeeForMultipleTimesWithinHour() throws Exception {

        TaxCalculatorRequest request = new TaxCalculatorRequest("Gothenburg", "Car",
                Arrays.asList(
                        LocalDateTime.of(2013, 8, 7, 7, 0),
                        LocalDateTime.of(2013, 8, 7, 7, 15),
                        LocalDateTime.of(2013, 8, 7, 7, 45)
                ));

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/congestion-tax/calculate-tax")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fee").value(18));
    }

    @Test
    @DisplayName("Tax for Multiple times within hour for two fee slot")
    public void testCalculateTaxFeeForMultipleTimesWithinHourForTwoFeeSlot() throws Exception {

        TaxCalculatorRequest request = new TaxCalculatorRequest("Gothenburg", "Car",
                Arrays.asList(
                        LocalDateTime.of(2013, 8, 7, 6, 45),
                        LocalDateTime.of(2013, 8, 7, 7, 15),
                        LocalDateTime.of(2013, 8, 7, 7, 30)
                ));

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/congestion-tax/calculate-tax")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fee").value(18));
    }

    @Test
    @DisplayName("Max 60 Tax Fee For One Day for valid vehicle")
    public void testCalculateTaxFeeForMaxFee() throws Exception {

        TaxCalculatorRequest request = new TaxCalculatorRequest("Gothenburg", "Car",
                Arrays.asList(
                        LocalDateTime.of(2013, 8, 7, 6, 45),
                        LocalDateTime.of(2013, 8, 7, 7, 50),
                        LocalDateTime.of(2013, 8, 7, 15, 15),
                        LocalDateTime.of(2013, 8, 7, 12, 15),
                        LocalDateTime.of(2013, 8, 7, 17, 15)
                ));

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/congestion-tax/calculate-tax")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fee").value(60));
    }
}
