package com.api.dev.controller;


import org.springframework.web.bind.annotation.*;

@RestController
public class DataController {

    @GetMapping("/sendTemplateMessage")
    public void getUser() {
        System.out.println("*****" + Math.random());
    }
}
