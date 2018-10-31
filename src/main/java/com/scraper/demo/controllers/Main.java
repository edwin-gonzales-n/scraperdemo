package com.scraper.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Main {

    @GetMapping("/")
    public String apartmentController(){
//        return "lenox-boardwalk";
        return "index";
    }

    @GetMapping("/fakelenox")
    public String fakelenox(){
        return "lenox-boardwalk";
    }

    @GetMapping("/sensors")
    public String sensorsController(){
        return "sensors";
    }
}
