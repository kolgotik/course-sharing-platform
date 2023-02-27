package com.example.webcustomertracker3.dao;

import com.example.webcustomertracker3.entity.Course;
import com.example.webcustomertracker3.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDAO extends JpaRepository<User, Integer> {
    void persist(User user);

    User findByUsername(String username);

    List<Course> getCoursesByUserId(int userId);
}
