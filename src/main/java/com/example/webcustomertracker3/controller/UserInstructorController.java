package com.example.webcustomertracker3.controller;

import com.example.webcustomertracker3.entity.Course;
import com.example.webcustomertracker3.service.StudentService;
import com.example.webcustomertracker3.service.UserService;
import com.example.webcustomertracker3.user.User;
import com.example.webcustomertracker3.user.UserRepository;
import com.example.webcustomertracker3.user.UserRole;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/instructor")
public class UserInstructorController {

    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/workspace")
    public String workspaceForCreators(Principal principal, Model model, HttpSession session) {
        String username = String.valueOf(session.getAttribute("username"));
        User user = userService.findByUsername(username);
        Course course = new Course();
        if (user.getUserRole().equals(UserRole.ROLE_INSTRUCTOR)) {
            model.addAttribute("course", course);
            return "workspace";
        }
        if (user.getUserRole().equals(UserRole.ROLE_STUDENT)) {
            return "for-creators-forbidden-err";
        }


        return username;
    }

    @PostMapping("/process-create-course")
    public String createCourseAsCreator(@ModelAttribute("course") Course course, HttpSession httpSession, Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        course.setAuthor(user.getUsername());
        course.setUser(user);
        userService.createCourse(course);
        return "default-success";
    }

    @GetMapping("/created-courses")
    public String showCreatedCourses(Principal principal, Model model) {

        User user = userService.findByUsername(principal.getName());
        List<Course> courseList = user.getCreatedCourses();
        model.addAttribute("createdCourses", courseList);

        return "created-courses-list";
    }

    @GetMapping("/edit-course/{id}")
    public String showFormToEditCourse(@PathVariable int id, Model model) {

        Course course = studentService.getCourse(id);
        model.addAttribute("course", course);

        return "course-edit";

    }

    @PostMapping("/process-edit-course/{id}")
    public String updateCourse(@PathVariable int id, @ModelAttribute("course") Course course, Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        studentService.updateCourse(course);
        return "redirect:/instructor/created-courses";
    }

    @GetMapping("/delete-course/{id}")
    public String deleteCourse(@PathVariable int id) {

        studentService.deleteCourse(id);

        return "redirect:/instructor/created-courses";
    }





    /*@GetMapping("/avatar/{fileName}")
    @ResponseBody
    public ResponseEntity<Resource> serveAvatar(@PathVariable String fileName, Principal principal, Model model) {
        String avatarUploadDir = "C:\\Users\\Kolgotik\\IdeaProjects\\github practice\\src\\main\\java\\com\\example\\webcustomertracker3\\avatars\\";
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        if (user == null || user.getAvatar() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Path path = Paths.get(avatarUploadDir + fileName);
        Resource resource;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).contentType(MediaType.IMAGE_JPEG).body(resource);
    }*/


}
