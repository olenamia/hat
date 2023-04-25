package com.mialyk.business.zillow.configuration;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "dbseeder.zillow")
@Data
public class ZillowConfig {

    private List<String> links;
    private String test;

    @JsonProperty("storagepath") 
    private String storagePath;
}
