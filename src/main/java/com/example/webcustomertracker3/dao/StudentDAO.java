package com.example.webcustomertracker3.dao;

import com.example.webcustomertracker3.entity.Course;

import java.util.List;

public interface StudentDAO {
    List<Course> getCourses();

    Course getCourse(int id);

    void addCourses(int id);

    void updateCourse(Course course);

    void deleteCourse(int id);

    String getAvatarByUsername(String username);
}
