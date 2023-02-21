package com.example.webcustomertracker3.controller;

import com.example.webcustomertracker3.entity.Course;
import com.example.webcustomertracker3.service.StudentService;
import com.example.webcustomertracker3.service.UserService;
import com.example.webcustomertracker3.user.User;
import com.example.webcustomertracker3.user.UserRepository;
import jakarta.servlet.http.HttpSession;
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
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/course-market")
    public String showCourses(Model model){

        List<Course> courses = studentService.getCourses();


        model.addAttribute("courses", courses);

        return "course-market";
    }

    @GetMapping("/show-more-info")
    public String showMoreInfo(@RequestParam("courseId") int id ,Model model){

        Course course = studentService.getCourse(id);

        model.addAttribute("course", course);

        return "course-info";
    }

    @GetMapping("/features")
    public String features(){

        return "features-page";
    }

    @GetMapping("/get-course")
    public String getCoursePage(@RequestParam String username, Principal principal, HttpSession session, Model model){

        /*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByUsername(username);
        User user1 = userService.authenticate(user.getUsername(),user.getPassword());

        model.addAttribute("user", user1);

        if (authentication.getPrincipal() != null && authentication.isAuthenticated()) {

            return "get-course";

        } else*/

            return "course-login";

    }
}
