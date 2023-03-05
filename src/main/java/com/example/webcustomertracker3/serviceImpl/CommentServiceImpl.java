package com.example.webcustomertracker3.serviceImpl;

import com.example.webcustomertracker3.dao.CommentRepo;
import com.example.webcustomertracker3.entity.Comment;
import com.example.webcustomertracker3.entity.Course;
import com.example.webcustomertracker3.service.CommentService;
import com.example.webcustomertracker3.service.StudentService;
import com.example.webcustomertracker3.service.UserService;
import com.example.webcustomertracker3.user.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;


    @Override
    @Transactional
    public void addComment(String username, int courseId, String content, Timestamp createdAt) {
        User user = userService.findByUsername(username);
        Course course = studentService.getCourse(courseId);
        createdAt = Timestamp.valueOf(LocalDateTime.now());

        if (user != null && course != null){
            String actualUsername = user.getUsername();
            Comment comment = new Comment();
            comment.setText(content);
            comment.setUser(user);
            comment.setCourse(course);
            comment.setUsername(actualUsername);
            comment.setTimestamp(createdAt);

            commentRepo.persist(comment);
        }
        else throw new EntityNotFoundException();
    }

    @Override
    @Transactional
    public List<Comment> getAllComments(int courseId) {
        return commentRepo.getAllComments(courseId);
    }
}
