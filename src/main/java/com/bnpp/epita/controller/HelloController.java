package com.bnpp.epita.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hello")
public class HelloController {
    @GetMapping("/user")
    String sayHelloUser() {
        return "hello user TP2";
    }
    @GetMapping("/admin")
    String sayHelloAdmin() {
        return "hello admin TP2";
    }

}
