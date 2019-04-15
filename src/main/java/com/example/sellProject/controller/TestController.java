package com.example.sellProject.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/hello")
    public String test1(){
        System.out.println("hello springboot");
        return "hello springboot";
    }

}
