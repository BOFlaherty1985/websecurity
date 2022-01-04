package com.investment.websecurity.jwt;

import com.investment.websecurity.web.model.AuthenticationRequest;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtility {

    private String secret_key = "test123";

    public String generateJwtToken(AuthenticationRequest authenticationRequest) {
        Map<String, Object> claims = new HashMap<>();
        return createJwtToken(claims, authenticationRequest.getUsername());
    }

    public String createJwtToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, secret_key)
                .compact();
    }

    // validate token

}
