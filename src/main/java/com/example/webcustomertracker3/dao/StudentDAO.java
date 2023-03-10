package com.example.webcustomertracker3.dao;

import com.example.webcustomertracker3.entity.Course;
import com.example.webcustomertracker3.user.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.security.Principal;
import java.util.List;

public interface StudentDAO {
    List<Course> getCourses();

    Course getCourse(int id);

    void addCourses(int id);

    void updateCourse(Course course);

    void deleteCourse(int id);
}
