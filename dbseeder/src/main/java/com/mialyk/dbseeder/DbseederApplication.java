package com.mialyk.dbseeder;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.mialyk.business.zillow.services.ZillowDataProcessor;

@SpringBootApplication
@ComponentScan(basePackages = {"com.mialyk.business"})
public class DbSeederApplication implements CommandLineRunner {
    @Autowired
    private ZillowDataProcessor zillowDataProcessor;

	public static void main(String[] args) {
		SpringApplication.run(DbSeederApplication.class, args);
        System.out.println("DBSEEDER RUN");
	}

    @Override
    public void run(String... args) throws URISyntaxException, IOException {
        //zillowDataProcessor.saveUrlsToStorageAndProcess();
        System.out.println("URLS PROCESSED");
    }
}
