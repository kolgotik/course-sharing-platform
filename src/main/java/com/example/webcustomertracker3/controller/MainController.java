package com.example.webcustomertracker3.controller;

import com.example.webcustomertracker3.entity.User;
import com.example.webcustomertracker3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String viewHomePage(){
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){

        model.addAttribute("user", new User());

        return "signup-form";
    }

}
