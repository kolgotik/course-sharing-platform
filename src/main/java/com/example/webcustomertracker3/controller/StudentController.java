package com.example.webcustomertracker3.controller;

import com.example.webcustomertracker3.entity.Course;
import com.example.webcustomertracker3.service.StudentService;
import com.example.webcustomertracker3.service.UserService;
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
import org.springframework.web.servlet.view.RedirectView;

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
    public RedirectView showCourses(Model model, Authentication authentication) {

        List<Course> courses = studentService.getCourses();

        model.addAttribute("courses", courses);

        if (authentication != null && authentication.isAuthenticated()) {
            return new RedirectView("/user-main");
        } else {
            return new RedirectView("/");
        }


    }

    @GetMapping("/show-more-info")
    public String showMoreInfo(@RequestParam("courseId") int id, Model model) {

        Course course = studentService.getCourse(id);

        model.addAttribute("course", course);

        return "course-info";
    }

    @GetMapping("/features")
    public String features() {

        return "features-page";
    }

    @GetMapping("/get-course")
    public String getCoursePage(@RequestParam("courseId") int id, Principal principal, HttpSession httpSession, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Course course = studentService.getCourse(id);

        if (authentication != null && authentication.isAuthenticated()) {

            httpSession.setAttribute("username", principal.getName());

            model.addAttribute("course", course);

            return "logged-get-course";

        } else

            return "course-login";

    }
}
