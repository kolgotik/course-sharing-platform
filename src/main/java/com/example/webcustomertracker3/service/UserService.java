package com.example.webcustomertracker3.service;

import com.example.webcustomertracker3.entity.Course;
import com.example.webcustomertracker3.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserService extends JpaRepository<User,Integer> {
    void persist(User user);

    User findByUsername(String username);

    User authenticate(String username, String password);

    List<Course> getCoursesByUserId(int userId);

    void deleteCourse(int courseId, int userId);
}
