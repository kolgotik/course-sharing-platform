package com.example.webcustomertracker3.controller;

import com.example.webcustomertracker3.entity.Course;
import com.example.webcustomertracker3.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private StudentService studentService;


    @GetMapping("/show-more-info")
    public String getCourseInfo(@RequestParam("courseId") int id , Model model){

        Course course = studentService.getCourse(id);

        model.addAttribute("course", course);

        return "logged-course-info";
    }

    @GetMapping("/get-course")
    public String getCoursePage(Principal principal){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()){
            return "logged-get-course";
        }else
            return "course-login";
    }

}
