package com.nand.assignment.congestiontaxcalculator.security.controller;

import com.nand.assignment.congestiontaxcalculator.security.model.request.GetTokenRequest;
import com.nand.assignment.congestiontaxcalculator.security.model.response.JwtAuthenticationResponse;
import com.nand.assignment.congestiontaxcalculator.security.service.ITokenProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class TokenProviderController {
    private final ITokenProviderService tokenProviderService;

    @PostMapping("/getToken")
    public ResponseEntity<JwtAuthenticationResponse> getJwtToken(@RequestBody GetTokenRequest request) {
        return ResponseEntity.ok(tokenProviderService.getToken(request));
    }
}
