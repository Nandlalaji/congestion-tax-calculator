package com.nand.assignment.congestiontaxcalculator.config;

import com.nand.assignment.congestiontaxcalculator.model.TaxFreeDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
@ConfigurationProperties(prefix = "tax-free-data")
@Getter
@Setter
@NoArgsConstructor
public class TaxFreeConfig {
    private List<TaxFreeDate> dates;
}
