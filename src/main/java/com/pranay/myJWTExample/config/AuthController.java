package com.pranay.myJWTExample.config;

import com.pranay.myJWTExample.model.JwtRequest;
import com.pranay.myJWTExample.model.JwtResponse;
import com.pranay.myJWTExample.services.JwtHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;


    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtHelper helper;

    private static final Logger LOGGER  = LoggerFactory.getLogger(AuthController.class);

    private void authenticate(String email, String password)
    {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email,password);
        try{
            manager.authenticate(authentication);
        }catch (Exception e)
        {
            throw  new RuntimeException("Invalid username and password !!!");
        }
    }

    @PostMapping("login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request)
    {
        authenticate(request.getEmail(),request.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = helper.generateToken(userDetails);
        JwtResponse response = new JwtResponse(userDetails.getUsername(),token);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
