package com.mialyk.dbseeder;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import com.mialyk.business.zillow.services.ZillowDataProcessor;
import com.mialyk.dbseeder.configuration.DbseederConfig;

@Import(DbseederConfig.class)
@ComponentScan(basePackages = {"com.mialyk.business"})
public class DbseederApplication implements CommandLineRunner {
    @Autowired
    private ZillowDataProcessor zillowDataProcessor;

	public static void main(String[] args) {
		SpringApplication.run(DbseederApplication.class, args);
	}

    @Override
    public void run(String... args) throws URISyntaxException, IOException {
        zillowDataProcessor.processAllStorage();
        //zillowDataProcessor.processAllRemote();
        //zillowDataProcessor.saveAllRemoteToStorage();
    }

}
