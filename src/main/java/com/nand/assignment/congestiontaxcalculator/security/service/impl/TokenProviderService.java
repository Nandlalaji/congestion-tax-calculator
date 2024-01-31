package com.nand.assignment.congestiontaxcalculator.security.service.impl;

import com.nand.assignment.congestiontaxcalculator.exception.InvalidInputException;
import com.nand.assignment.congestiontaxcalculator.security.config.UserConfig;
import com.nand.assignment.congestiontaxcalculator.security.model.request.GetTokenRequest;
import com.nand.assignment.congestiontaxcalculator.security.model.response.JwtAuthenticationResponse;
import com.nand.assignment.congestiontaxcalculator.security.service.ITokenProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenProviderService implements ITokenProviderService {
    private final UserConfig userConfig;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthenticationResponse getToken(GetTokenRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        var user = userConfig.getUsers().stream()
                .filter(u -> u.email().equalsIgnoreCase(request.email()))
                .findFirst()
                .orElseThrow(() -> new InvalidInputException("Invalid email or password."));
        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }
}
