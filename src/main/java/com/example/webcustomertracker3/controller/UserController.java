package com.example.webcustomertracker3.controller;

import com.example.webcustomertracker3.entity.Comment;
import com.example.webcustomertracker3.entity.Course;
import com.example.webcustomertracker3.service.CommentService;
import com.example.webcustomertracker3.service.StudentService;
import com.example.webcustomertracker3.service.UserService;
import com.example.webcustomertracker3.user.User;
import com.example.webcustomertracker3.user.UserRepository;
import com.example.webcustomertracker3.user.UserRole;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ResourceLoader resourceLoader;




    @GetMapping("/show-more-info")
    public String getCourseInfo(@RequestParam("courseId") int id, Model model, Principal principal, HttpSession httpSession) {

        User user = userService.findByUsername(principal.getName());

        Course course = studentService.getCourse(id);
        String avatar = studentService.getAvatarByUsername(user.getUsername());
        List<Comment> comments = commentService.getAllComments(id);

        model.addAttribute("comments", comments);
        model.addAttribute("course", course);
        model.addAttribute("user", user);
        model.addAttribute("avatar", avatar);
        model.addAttribute("avatar", user.getAvatar());
        httpSession.setAttribute("avatar", avatar);

        return "logged-course-info";
    }

    @PostMapping("/avatar")
    public String uploadAvatar(@RequestParam("avatarFile") MultipartFile avatar, Principal principal) {
        User user = userService.findByUsername(principal.getName());

        if (avatar.isEmpty()) {
            return "redirect:/user/profile";
        }
        String fileName;
        try {
            fileName = UUID.randomUUID().toString() + "." + StringUtils.getFilenameExtension(avatar.getOriginalFilename());

            byte[] bytes = avatar.getBytes();
            Path path = Paths.get("src/main/resources/static/avatars/" + fileName);
            Files.write(path, bytes);

            if (user.getAvatar() != null) {
                Path oldPath = Paths.get("src/main/resources/static/avatars/" + user.getAvatar());
                Files.deleteIfExists(oldPath);

            }

            user.setAvatar(fileName);
            userService.persist(user);
            System.out.println("Line 120 UserController: " +  fileName);

            System.out.println("Line 126 UserController: " + path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "redirect:/user/profile";
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

    @PostMapping("/delete-course/{id}")
    public String deleteCourses(@PathVariable int id, Principal principal, Model model) {

        String username = principal.getName();

        User user = userService.findByUsername(username);

        Course course = studentService.getCourse(id);

        List<Course> courses = userService.getCoursesByUserId(id);

        userService.deleteCourse(course.getId(), user.getId());

        return "redirect:/user/my-courses";
    }

    @GetMapping("/profile")
    public String myProfile(Principal principal, Model model) {

        User user = userService.findByUsername(principal.getName());
        model.addAttribute("avatar", user.getAvatar());
        model.addAttribute("user", user);
        if (user.getUserRole().equals(UserRole.ROLE_INSTRUCTOR)) {

            return "instructor-profile";
        }

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

    @GetMapping("/open-course/{id}")
    public String openCourse(@PathVariable int id, Model model) {

        Course course = studentService.getCourse(id);
        model.addAttribute("course", course);

        return "course-content-page";

    }
}
