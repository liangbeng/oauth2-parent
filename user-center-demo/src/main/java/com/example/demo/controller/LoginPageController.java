package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class LoginPageController {

    @RequestMapping("/")
    public RedirectView loginPage(){
        return new RedirectView("/login.html");
    }
}
