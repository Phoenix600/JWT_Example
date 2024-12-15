package com.pranay.myJWTExample.config;


import com.pranay.myJWTExample.constants.AppConstants;
import com.pranay.myJWTExample.security.JwtAuthenticationEntryPoint;
import com.pranay.myJWTExample.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Autowired
    private JwtAuthenticationFilter filter;

    @Autowired
    private JwtAuthenticationEntryPoint point;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //  configuration
        http.cors(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(
                request->
                        request
                                .requestMatchers(AppConstants.DUMMY_BASE_API_PATTERN).authenticated()
                                .requestMatchers(AppConstants.HOME_BASE_API_PATTERN).authenticated()
                                .requestMatchers("/api/v1/auth/**").permitAll()
                );

        http.exceptionHandling(e->e.authenticationEntryPoint(point));
        http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
