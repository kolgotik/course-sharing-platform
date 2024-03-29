package com.example.webcustomertracker3.serviceImpl;

import com.example.webcustomertracker3.dao.StudentDAO;
import com.example.webcustomertracker3.dao.UserDAO;
import com.example.webcustomertracker3.entity.Course;
import com.example.webcustomertracker3.service.StudentService;
import com.example.webcustomertracker3.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDAO studentDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public List<Course> getCourses() {
        return studentDAO.getCourses();
    }

    @Override
    @Transactional
    public Course getCourse(int id) {
        return studentDAO.getCourse(id);
    }

    @Override
    @Transactional
    public void addCourses(int id) {
        studentDAO.addCourses(id);
    }

    @Override
    @Transactional
    public void updateCourse(Course course) {
        studentDAO.updateCourse(course);
    }

    @Override
    @Transactional
    public void deleteCourse(int id) {
        studentDAO.deleteCourse(id);
    }

    @Override
    @Transactional
    public String getAvatarByUsername(String username) {

        return studentDAO.getAvatarByUsername(username);
    }


}
