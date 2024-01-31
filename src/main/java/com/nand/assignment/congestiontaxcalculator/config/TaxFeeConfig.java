package com.nand.assignment.congestiontaxcalculator.config;

import com.nand.assignment.congestiontaxcalculator.model.TaxFee;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "tax-fee-data")
@Getter
@Setter
public class TaxFeeConfig {
    private Map<String, List<TaxFee>> byCity;

    public List<TaxFee> getTaxByCity(String cityName) {
        return byCity.get(cityName);
    }
}
