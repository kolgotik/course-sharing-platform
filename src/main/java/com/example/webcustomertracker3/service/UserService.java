package com.example.webcustomertracker3.service;

import com.example.webcustomertracker3.entity.Course;
import com.example.webcustomertracker3.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService extends JpaRepository<User,Integer> {
    void persist(User user);

    User findByUsername(String username);

    User authenticate(String username, String password);

    List<Course> getCoursesByUserId(int userId);

    void deleteCourse(int courseId, int userId);

    boolean isUsernameUnique(String username);
    boolean isEmailUnique(String email);

    void createCourse(Course course);

    List<User> getUsers();

    void updateUserAvatarFromComment(String oldFileName, String newFileName);

    List<Course> searchCourse(String courseTitle);

}
