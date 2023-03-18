package com.example.webcustomertracker3.controller;


import com.example.webcustomertracker3.user.User;
import com.example.webcustomertracker3.service.UserService;
import com.example.webcustomertracker3.user.UserRepository;
import com.example.webcustomertracker3.user.UserRole;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RegistrationAndLoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {

        model.addAttribute("user", new User());


        return "signup-form";
    }

    @GetMapping("/register-as-creator")
    public String showRegistrationFormForCreators(Model model) {

        model.addAttribute("user", new User());


        return "sign-up-as-creator";
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {


        return "login";
    }

    @PostMapping("/process-login")
    public String processLogin(@RequestParam String username, @RequestParam String password, HttpSession session, Principal principal, Model model) {

        User user = userService.authenticate(username, password);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (user != null && authentication.isAuthenticated()) {
            session.setAttribute("user", user);
            session.setAttribute("userId", user.getId());
            session.setAttribute("username", user.getUsername());
            return "redirect:/";
        } else
            return "redirect:/login?error";

    }

    @PostMapping("/process-register-as-creator")
    public String processRegisterAsCreator(User user, Model model) {
        String username = user.getUsername();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        boolean isUsernameUnique = userService.isUsernameUnique(username);
        boolean isEmailUnique = userService.isEmailUnique(user.getEmail());

        if (isUsernameUnique && isEmailUnique){
            user.setPassword(encodedPassword);
            user.setUserRole(UserRole.ROLE_INSTRUCTOR);
            userService.persist(user);
            return "register-success";
        }
        if (!isUsernameUnique){
            model.addAttribute("nonUniqueUsernameErr", "this username is already in use choose another");
            return "nonUniqueUsernameForCreatorErr";
        }
        if (!isEmailUnique){
            model.addAttribute("nonUniqueEmailForCreatorErr", "this email is occupied choose another");
            return "nonUniqueEmailErr";
        }
        return "";
    }

    @PostMapping("/process-register")
    public String processRegister(User user, Model model) {
        String username = user.getUsername();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        boolean isUsernameUnique = userService.isUsernameUnique(username);
        boolean isEmailUnique = userService.isEmailUnique(user.getEmail());
        if (isUsernameUnique && isEmailUnique){
            user.setPassword(encodedPassword);
            user.setUserRole(UserRole.ROLE_STUDENT);
            userService.persist(user);
            return "register-success";
        }
        if (!isUsernameUnique){
            model.addAttribute("nonUniqueUsernameErr", "this username is already in use choose another");
            return "nonUniqueUsernameErr";
        }
        if (!isEmailUnique){
            model.addAttribute("nonUniqueEmailErr", "this email is occupied choose another");
            return "nonUniqueEmailErr";
        }
        return "";
    }

    @GetMapping("/course-login")
    public String showCourseLoginPage() {
        return "course-login";
    }

    @GetMapping("/login-err")
    public String cantHaveMoreThanOneSessionError(Model model) {
        model.addAttribute("alreadyLoggedIn", "this user is already logged in");
        return "user-already-logged-err";
    }

}
