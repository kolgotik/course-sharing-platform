package com.example.webcustomertracker3.service;

import com.example.webcustomertracker3.entity.Course;

import java.util.List;

public interface StudentService {

    List<Course> getCourses();

    Course getCourse(int id);
}
