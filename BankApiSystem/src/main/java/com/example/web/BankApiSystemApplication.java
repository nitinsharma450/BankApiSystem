package com.example.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.web")
public class BankApiSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankApiSystemApplication.class, args);
	}

}
