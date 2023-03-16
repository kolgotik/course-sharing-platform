package com.example.webcustomertracker3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebCustomerTracker3Application {

	static String dir = "/static/avatars/";
	public static void main(String[] args) {
		dir = "static/avatars/";
		SpringApplication.run(WebCustomerTracker3Application.class, args);
	}

}
