package com.example.webcustomertracker3.service;

import com.example.webcustomertracker3.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserService extends JpaRepository<User,Integer> {
}
