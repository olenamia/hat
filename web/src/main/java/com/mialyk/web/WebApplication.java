package com.mialyk.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.mialyk.persistence", "com.mialyk.business.services","com.mialyk.web.controllers","com.mialyk.business.mappers"})
public class WebApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
        System.out.println("WEB STARTED");
	}

}
