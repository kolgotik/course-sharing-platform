package com.example.webcustomertracker3.controller;

import com.example.webcustomertracker3.entity.Course;
import com.example.webcustomertracker3.entity.Student;
import com.example.webcustomertracker3.service.StudentService;
import com.example.webcustomertracker3.service.UserService;
import com.example.webcustomertracker3.user.User;
import com.example.webcustomertracker3.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;


@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentService studentService;


    @GetMapping("/")
    public String viewHomePage(Model model, HttpServletRequest httpServletRequest, Principal principal, HttpSession httpSession) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();



        List<Course> courses = studentService.getCourses();

        model.addAttribute("courses", courses);
        model.addAttribute("httpServletRequest", httpServletRequest);

        if (principal != null) {
            User user = userService.findByUsername(principal.getName());
            httpSession.setAttribute("username", principal.getName());
            model.addAttribute("user", user);

            return "logged-index";

        } else

            return "index";


    }
}








