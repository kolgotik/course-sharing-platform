package com.example.webcustomertracker3.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
public class TestController {

    @RequestMapping("/test")
    public void test(HttpServletResponse response) throws IOException {
        response.getWriter().println("Hello from Servlet");
    }
}

