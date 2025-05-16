package com.pm.devautomation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DummyController {
    @GetMapping("index")
    public String start(){
        return "Hello World";
    }
}
