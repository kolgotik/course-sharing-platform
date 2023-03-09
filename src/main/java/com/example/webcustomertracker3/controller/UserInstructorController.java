package com.example.webcustomertracker3.controller;

import com.example.webcustomertracker3.entity.Course;
import com.example.webcustomertracker3.service.UserService;
import com.example.webcustomertracker3.user.User;
import com.example.webcustomertracker3.user.UserRole;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/instructor")
public class UserInstructorController {

    @Autowired
    private UserService userService;

    @GetMapping("/workspace")
    public String workspaceForCreators(Principal principal, Model model, HttpSession session){
        String username = String.valueOf(session.getAttribute("username"));
        User user = userService.findByUsername(username);
        Course course = new Course();
        if (user.getUserRole().equals(UserRole.ROLE_INSTRUCTOR)){
            model.addAttribute("course", course);
            return "workspace";
        }
        if (user.getUserRole().equals(UserRole.ROLE_STUDENT)){
            return "for-creators-forbidden-err";
        }


        return username;
    }
    @PostMapping("/process-create-course")
    public String createCourseAsCreator(@ModelAttribute("course") Course course, HttpSession httpSession, Principal principal, Model model){
        User user = userService.findByUsername(principal.getName());
        course.setAuthor(user.getUsername());
        course.setUser(user);
        userService.createCourse(course);
        return "default-success";
    }

    @GetMapping("/created-courses")
    public String showCreatedCourses(Principal principal, Model model){

        User user = userService.findByUsername(principal.getName());
        List<Course> courseList = user.getCreatedCourses();
        model.addAttribute("createdCourses", courseList);

        return "created-courses-list";
    }
}
