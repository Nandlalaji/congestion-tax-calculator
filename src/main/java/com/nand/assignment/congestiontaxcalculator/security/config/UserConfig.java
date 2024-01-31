package com.nand.assignment.congestiontaxcalculator.security.config;

import com.nand.assignment.congestiontaxcalculator.security.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "users-data")
@Getter
@Setter
public class UserConfig {
    List<User> users;
}
