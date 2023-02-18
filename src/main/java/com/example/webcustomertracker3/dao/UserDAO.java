package com.example.webcustomertracker3.dao;

import com.example.webcustomertracker3.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User, Integer> {
    void persist(User user);
}
