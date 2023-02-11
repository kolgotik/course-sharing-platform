package com.example.webcustomertracker3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student")
public class StudentController {

    @GetMapping("/course-market")
    public String showCourses(Model model){

        return "course-market";
    }

    @GetMapping("/features")
    public String featuresPage(Model model){

        return "features";
    }
}
