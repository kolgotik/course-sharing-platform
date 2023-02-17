package com.example.webcustomertracker3.controller;

import com.example.webcustomertracker3.entity.Course;
import com.example.webcustomertracker3.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

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
    public String getCoursePage(){

        return "get-course";
    }
}
