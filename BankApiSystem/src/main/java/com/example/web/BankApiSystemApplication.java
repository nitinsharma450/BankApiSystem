package com.example.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={
"com.example.web.Controller", "com.example.web.service","com.example.web.repository"})
public class BankApiSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankApiSystemApplication.class, args);
	}

}
