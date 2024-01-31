package com.nand.assignment.congestiontaxcalculator.security.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService {
    UserDetailsService userDetailsService();
}
