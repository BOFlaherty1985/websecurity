package com.investment.websecurity;

import com.com.investment.websecuritylibrary.jwt.JwtUtility;
import com.com.investment.websecuritylibrary.service.CustomUserDetailService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AuthenticationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthenticationServerApplication.class, args);
    }

    @Bean
    public JwtUtility jwtUtility() {
        return new JwtUtility();
    }

    @Bean
    public CustomUserDetailService customUserDetailService() {
        return new CustomUserDetailService();
    }

}
