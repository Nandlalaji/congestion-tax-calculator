package com.nand.assignment.congestiontaxcalculator.config;

import com.nand.assignment.congestiontaxcalculator.model.Vehicle;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "vehicles-data")
@Getter
@Setter
public class VehicleConfig {
    private List<Vehicle> vehicles;
}
