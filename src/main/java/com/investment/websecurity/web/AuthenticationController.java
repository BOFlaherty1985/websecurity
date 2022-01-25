package com.investment.websecurity.web;

import com.com.investment.websecuritylibrary.jwt.JwtUtility;
import com.com.investment.websecuritylibrary.web.model.AuthenticationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import server.authenticationserver.AuthenticationServerResponse;
import server.authenticationserver.AuthenticationServerResponseBuilder;

@RestController
@Slf4j
public class AuthenticationController {

    private JwtUtility jwtUtility;
    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationController(JwtUtility jwtUtility, AuthenticationManager authenticationManager) {
        this.jwtUtility = jwtUtility;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/authenticate")
    public ResponseEntity<AuthenticationServerResponse> authenticate(@RequestParam String username,
                                                                     @RequestParam String password) {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername(username);
        request.setPassword(password);
        log.info("Attempting to authenticate user " + request.getUsername());
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException("user not found.");
        }

        String jwtToken = jwtUtility.generateJwtToken(request);
        AuthenticationServerResponse response = AuthenticationServerResponseBuilder.authenticationServerResponseBuilder()
                .jwtToken(jwtToken)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/test")
    public void testMapping() {
        log.info("test endpoint");
    }
}