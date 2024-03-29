package com.example.webcustomertracker3.controller;

import com.example.webcustomertracker3.entity.Course;
import com.example.webcustomertracker3.service.StudentService;
import com.example.webcustomertracker3.service.UserService;
import com.example.webcustomertracker3.user.User;
import com.example.webcustomertracker3.user.UserRepository;
import jakarta.persistence.Access;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class LoggedInMainController {

    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user-main")
    public String mainPageForLoggedUsers(String username, Model model, Principal principal, HttpSession httpSession) {

       User user = userRepository.findByUsername(username);


        List<Course> courses = studentService.getCourses();

        //httpSession.setAttribute("username",principal.getName());
        httpSession.setAttribute("user", user);
        httpSession.setAttribute("userId", user.getId());
        httpSession.setAttribute("username", principal.getName());

        model.addAttribute("user", user);
        model.addAttribute("courses", courses);
        model.addAttribute("username", user.getUsername());


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

      /*  if (httpSession.getAttribute("user") == null) {
            return "index";
        }
         else if (httpSession.getAttribute("user") != null) {
            return "logged-index";
        }*/

        if (authentication != null && authentication.isAuthenticated()) {

            return "logged-index";

        } else

            return "index";
    }

}
