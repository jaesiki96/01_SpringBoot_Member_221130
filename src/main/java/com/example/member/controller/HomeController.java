package com.example.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    //기본주소
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
