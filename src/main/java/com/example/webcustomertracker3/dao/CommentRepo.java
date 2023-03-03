package com.example.webcustomertracker3.dao;

import com.example.webcustomertracker3.entity.Comment;
import com.example.webcustomertracker3.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
    void persist(Comment comment);

    List<Comment> getAllComments(int courseId);

}
