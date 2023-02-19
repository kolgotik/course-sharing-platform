package com.example.webcustomertracker3.controller;

import com.example.webcustomertracker3.user.User;
import com.example.webcustomertracker3.service.UserService;
import com.example.webcustomertracker3.user.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationAndLoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model){

        model.addAttribute("user", new User());

        return "signup-form";
    }

    @GetMapping("/login")
    public String showLoginPage(){

        return "login";
    }


    @PostMapping("/process-register")
    public String processRegister(User user){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setUserRole(UserRole.ROLE_STUDENT);
        userService.persist(user);

        return "register-success";
    }

    @GetMapping("/course-login")
    public String showCourseLoginPage(){
        return "course-login";
    }
}