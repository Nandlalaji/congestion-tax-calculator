package com.nand.assignment.congestiontaxcalculator.security.service;

import com.nand.assignment.congestiontaxcalculator.security.model.request.GetTokenRequest;
import com.nand.assignment.congestiontaxcalculator.security.model.response.JwtAuthenticationResponse;

public interface ITokenProviderService {

    JwtAuthenticationResponse getToken(GetTokenRequest request);
}
