package com.mialyk.web;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ComponentScan(basePackages = {"com.mialyk.persistence", "com.mialyk.business.services","com.mialyk.web.controllers","com.mialyk.business.mappers"})
public class WebApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
        System.out.println("WEB STARTED");
	}

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("hat-api")
                .pathsToMatch("/hat/**")
                .build();
    }
}
