package com.pranay.myJWTExample.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class AppConfig {


    ///  Step-1: Create Password Encoder
    @Bean
    PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    /// USer Details Service, Creating InMemory User Entities
    @Bean
    public UserDetailsService  userDetailsService()
    {
        PasswordEncoder passwordEncoder = passwordEncoder();
        UserDetails userDetails = User.builder()
                .username("pranay")
                .password(passwordEncoder.encode("password"))
                .roles("ADMIN")
                .build();

        UserDetails userDetails1 = User.builder()
                .username("rajesh")
                .password(passwordEncoder.encode("root@123"))
                .build();


        return new InMemoryUserDetailsManager(userDetails,userDetails1);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }



}
