package com.mialyk.business.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"com.mialyk.persistence.repositories"})
@EntityScan(basePackages = {"com.mialyk.persistence.entities"})
@ComponentScan(basePackages = {"com.mialyk.persistence"})
public class BusinessConfiguration {
    
}
