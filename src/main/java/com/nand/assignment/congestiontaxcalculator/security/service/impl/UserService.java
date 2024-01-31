package com.nand.assignment.congestiontaxcalculator.security.service.impl;

import com.nand.assignment.congestiontaxcalculator.security.config.UserConfig;
import com.nand.assignment.congestiontaxcalculator.security.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserConfig userConfig;
    ;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userConfig.getUsers().stream()
                        .filter(u -> u.email().equalsIgnoreCase(username))
                        .findFirst()
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }
}
