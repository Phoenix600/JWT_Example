package com.pranay.myJWTExample.controller;


import com.pranay.myJWTExample.constants.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dummy/")
public class DummyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DummyController.class);

    @GetMapping("test")
    public String test()
    {
        LOGGER.info("Test GET Called");
        return "Testing Message";
    }

}
