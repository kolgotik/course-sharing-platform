package com.example.webcustomertracker3.controller;

import com.example.webcustomertracker3.entity.Comment;
import com.example.webcustomertracker3.entity.Course;
import com.example.webcustomertracker3.service.CommentService;
import com.example.webcustomertracker3.service.StudentService;
import com.example.webcustomertracker3.service.UserService;
import com.example.webcustomertracker3.user.User;
import com.example.webcustomertracker3.user.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    @Autowired
    private CommentService commentService;

    @Autowired
    private ResourceLoader resourceLoader;

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

    @GetMapping("/avatars/{imageName}")
    @ResponseBody
    public ResponseEntity<Resource> getAvatar(@PathVariable String imageName) {
        Resource resource = resourceLoader.getResource("file:D:\\avatars\\" + imageName);
        System.out.println("Line 66 StudentController: " + imageName);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource);

    }

    @GetMapping("/show-more-info")
    public String showMoreInfo(@RequestParam("courseId") int id, Model model, Principal principal, HttpSession httpSession) {

        Course course = studentService.getCourse(id);

        List<Comment> comments = commentService.getAllComments(id);
        String username = (String) httpSession.getAttribute("username");
//        User user = userService.findByUsername(username);
        //String avatar = studentService.getAvatarByUsername(user.getUsername());
        List<User> users = userService.getUsers();
        model.addAttribute("comments", comments);

        model.addAttribute("course", course);

        //model.addAttribute("avatar", avatar);
        model.addAttribute("users", users);
        //System.out.println("avatars from: " + avatar + course);

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
            User user = userService.findByUsername(principal.getName());
            httpSession.setAttribute("username", principal.getName());
            model.addAttribute("course", course);
            model.addAttribute("user", user);
            return "logged-get-course";

        } else

            return "course-login";

    }
    @GetMapping("/search-for-course")
    private String searchForCourse(@RequestParam("courseTitle") String title, Model model){
        List<Course> courses = userService.searchCourse(title);
        model.addAttribute("courses", courses);
        return "index";
    }

}
