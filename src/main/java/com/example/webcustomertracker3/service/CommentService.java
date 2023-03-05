package com.example.webcustomertracker3.service;

import com.example.webcustomertracker3.entity.Comment;
import jakarta.servlet.http.HttpSession;

import java.sql.Timestamp;
import java.util.List;

public interface CommentService {
    void addComment(String username, int courseId, String content, Timestamp createdAt);

    List<Comment> getAllComments(int courseId);
}
