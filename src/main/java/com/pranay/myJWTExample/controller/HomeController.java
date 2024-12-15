package com.pranay.myJWTExample.controller;


import com.pranay.myJWTExample.model.User;
import com.pranay.myJWTExample.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/home/")
public class HomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private UserService userService;

    @GetMapping("users")
    public List<User> getAllUsers()
    {
        LOGGER.info("HomeController -> getAllUsers() http handler method");
        return  userService.getUsers();
    }

    // Principle Object Represents the current loggedIn user (Subject)
    @GetMapping("user")
    public String getLoggedInUser(Principal user)
    {
        return user.getName();
    }




}
