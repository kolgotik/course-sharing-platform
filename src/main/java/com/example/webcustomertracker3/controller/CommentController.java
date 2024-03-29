package com.example.webcustomertracker3.controller;

import com.example.webcustomertracker3.entity.Course;
import com.example.webcustomertracker3.service.CommentService;
import com.example.webcustomertracker3.service.StudentService;
import com.example.webcustomertracker3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.sql.Timestamp;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserService userService;

    @PostMapping("/post-comment/{username}&{courseId}")
    public String postComment(@PathVariable String username, @PathVariable int courseId, Principal principal, @RequestParam("comment") String text,
                              Model model, Timestamp timestamp) {

        Course course = studentService.getCourse(courseId);

        model.addAttribute("course", course);

        commentService.addComment(username, courseId, text, timestamp);

        return "redirect:/user/show-more-info?courseId=" + courseId;
    }

}
