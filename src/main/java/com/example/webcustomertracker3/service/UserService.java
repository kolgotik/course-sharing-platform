package com.example.webcustomertracker3.service;

import com.example.webcustomertracker3.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserService extends JpaRepository<User,Integer> {
    void persist(User user);

    User authenticate(String username, String password);
}
