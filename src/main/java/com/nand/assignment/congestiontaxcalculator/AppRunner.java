package com.nand.assignment.congestiontaxcalculator;

import com.nand.assignment.congestiontaxcalculator.config.AppConfig;
import com.nand.assignment.congestiontaxcalculator.config.TaxFeeConfig;
import com.nand.assignment.congestiontaxcalculator.config.TaxFreeConfig;
import com.nand.assignment.congestiontaxcalculator.config.VehicleConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({AppConfig.class, TaxFreeConfig.class, TaxFeeConfig.class, VehicleConfig.class})
public class AppRunner {

    public static void main(String[] args) {
        SpringApplication.run(AppRunner.class, args);
    }
}
