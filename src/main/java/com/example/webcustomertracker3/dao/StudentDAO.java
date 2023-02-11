package com.example.webcustomertracker3.dao;

import com.example.webcustomertracker3.entity.Course;

import java.util.List;

public interface StudentDAO {
    List<Course> getCourses();

    Course getCourse(int id);
}
