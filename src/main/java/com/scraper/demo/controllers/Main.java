package com.scraper.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Main {

    @GetMapping("/")
    public String apartmentController(){
//        return "index";
//        return "lenox-boardwalk";
        return "index";
    }

    @GetMapping("/sensors")
    public String sensorsController(){
        return "sensors";
    }
}
