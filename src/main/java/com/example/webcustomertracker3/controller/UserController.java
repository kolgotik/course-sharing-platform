package com.example.webcustomertracker3.controller;

import com.example.webcustomertracker3.entity.Course;
import com.example.webcustomertracker3.service.StudentService;
import com.example.webcustomertracker3.service.UserService;
import com.example.webcustomertracker3.user.User;
import com.example.webcustomertracker3.user.UserRepository;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/show-more-info")
    public String getCourseInfo(@RequestParam("courseId") int id, Model model) {

        Course course = studentService.getCourse(id);

        model.addAttribute("course", course);

        return "logged-course-info";
    }

    @GetMapping("/get-course")
    public String getCoursePage(Principal principal, @RequestParam("courseId") int id, Model model) {

        Course course = studentService.getCourse(id);

        model.addAttribute("course", course);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            return "logged-get-course";
        } else
            return "course-login";
    }

    /*@PostMapping("/process-get-course/{id}")
    public String addCourseToAccount(@RequestParam int id, Model model, HttpSession httpSession, Principal principal){

        studentService.addCourses(id);

        Course course = studentService.getCourse(id);

        model.addAttribute("myCourse", course);

        User user = (User) httpSession.getAttribute("user");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (principal != null && authentication.isAuthenticated()){
            return "redirect:/user/my-courses";
        }else
            return "redirect:/login";

    }*/

    @PostMapping("/process-get-course/{id}")
    public String addCourseToAccount(@PathVariable int id, Model model, HttpSession httpSession) {

        String name = (String) httpSession.getAttribute("username");

        User user = userService.findByUsername(name);

        Course course = studentService.getCourse(id);

        List<Course> userCourses = user.getCourses();


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (userCourses.contains(course)) {
            model.addAttribute("message", "You already have this course");
            return "add-same-course-err";
        } else
            user.getCourses().add(course);
        userService.persist(user);

        if (user != null && authentication.isAuthenticated()) {
            return "redirect:/user/my-courses";
        } else
            return "redirect:/login";

    }

    @GetMapping("/profile")
    public String myProfile() {

        return "profile";
    }

    @GetMapping("/my-courses")
    public String myCourses(Model model, HttpSession httpSession, Principal principal) {

        String username = principal.getName();

        User user = userService.findByUsername(username);

        List<Course> courses = userService.getCoursesByUserId(user.getId());

        model.addAttribute("courses", courses);

        return "course-list";
    }

}
