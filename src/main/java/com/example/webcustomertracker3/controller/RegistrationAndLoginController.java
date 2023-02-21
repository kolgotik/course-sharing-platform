package com.example.webcustomertracker3.controller;

import com.example.webcustomertracker3.user.User;
import com.example.webcustomertracker3.service.UserService;
import com.example.webcustomertracker3.user.UserRepository;
import com.example.webcustomertracker3.user.UserRole;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class RegistrationAndLoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {

        model.addAttribute("user", new User());

        return "signup-form";
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {

        /*User user = userRepository.findByUsername();*/

        return "login";
    }

    @PostMapping("/process-login")
    public String processLogin(@RequestParam String username, @RequestParam String password, HttpSession session, Principal principal) {

        User user = userService.authenticate(username, password);

        if (user != null) {
            session.setAttribute("userId", user.getId());
            session.setAttribute("username", user.getUsername());
            return "redirect:/user-main";
        } else
            return "redirect:/login?error";
    }


    @PostMapping("/process-register")
    public String processRegister(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setUserRole(UserRole.ROLE_STUDENT);
        userService.persist(user);

        return "register-success";
    }

    @GetMapping("/course-login")
    public String showCourseLoginPage() {
        return "course-login";
    }
}
