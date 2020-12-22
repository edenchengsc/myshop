package com.edencheng.myshop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloMyShop {

    @GetMapping
    public String helloMyShop(){
        return "Hello My shop. Hello git!";
    }
}
