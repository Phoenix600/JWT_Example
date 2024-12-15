package com.pranay.myJWTExample.security;

import com.pranay.myJWTExample.services.JwtHelper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestHeader = request.getHeader("Authorization");
        // Bearer 235426542364diva
        LOGGER.info("Header : {}",requestHeader);

        String username = null;
        String token = null;

        if(requestHeader != null && requestHeader.startsWith("Bearer"))
        {
            token = requestHeader.substring(7);
            try {
                username = jwtHelper.getUserNameFromToken(token);
            }
            catch (Exception e)
            {
                logger.info("error : " + e.getMessage());
            }
        }
        else
        {
            LOGGER.info("Invalid Header Value !!");
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null)
        {
            // Fetching UserDetails from username from spring security context
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            Boolean validateToken = jwtHelper.validateToken(token, userDetails);

            if(validateToken)
            {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            else
            {
                LOGGER.info("Token Validation Fails !!");
            }
        }

        filterChain.doFilter(request,response);

    }


}
