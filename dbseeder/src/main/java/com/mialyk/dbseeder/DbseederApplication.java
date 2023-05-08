package com.mialyk.dbseeder;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import com.mialyk.business.zillow.services.ZillowDataProcessor;
import com.mialyk.dbseeder.configuration.DbSeederConfig;

@Import(DbSeederConfig.class)
@ComponentScan(basePackages = {"com.mialyk.business"})
public class DbSeederApplication implements CommandLineRunner {
    @Autowired
    private ZillowDataProcessor zillowDataProcessor;

	public static void main(String[] args) {
		SpringApplication.run(DbSeederApplication.class, args);
        System.out.println("DBSEEDER STARTED");
	}

    @Override
    public void run(String... args) throws URISyntaxException, IOException {
        //zillowDataProcessor.processAllStorage();
        //zillowDataProcessor.processAllRemote();
        //zillowDataProcessor.saveAllRemoteToStorage();
    }
}
