package com.investment.websecurity.web;

import com.investment.websecurity.jwt.JwtUtility;
import com.investment.websecurity.web.model.AuthenticationRequest;
import com.investment.websecurity.web.model.AuthenticationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.HashMap;

@RestController
@Slf4j
public class AuthenticationController {

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody  AuthenticationRequest request) {
        log.info("Attempting to authenticate user " + request.getUsername());
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException("user not found.");
        }

        String jwtToken = jwtUtility.generateJwtToken(request);
        AuthenticationResponse response = new AuthenticationResponse();
        response.setJwtToken(jwtToken);
        return ResponseEntity.ok(response);
    }
}