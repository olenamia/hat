package com.mialyk.business.zillow.services;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mialyk.business.common.FileWorker;
import com.mialyk.business.zillow.configuration.ZillowConfig;
import com.mialyk.business.zillow.dtos.ZvhiStatesDto;

@Component
public class ZillowDataProcessor {
    @Autowired
    private FileWorker fileDownloader;
    @Autowired
    private ZillowCsvParser csvParser;
    @Autowired
    private ZillowDataStorageService dataProcessingService;
    @Autowired
    private ZillowConfig config;

    public void processRemote (String url) throws URISyntaxException, IOException {
        String content = fileDownloader.getRemoteContent(url);
        List<ZvhiStatesDto> zillowDataDto = csvParser.getHomeValues(content);
        dataProcessingService.saveHomeValues(zillowDataDto);
    }

    public void processAllRemote () throws URISyntaxException, IOException {
        for (String link : config.getLinks()) {
            processRemote(link);
        }
    }

    public void processStorage (String storagePath) throws URISyntaxException, IOException {
        Reader fileReader = Files.newBufferedReader(Paths.get(storagePath));

        List<ZvhiStatesDto> zillowDataDto = csvParser.getHomeValues(fileReader);
        dataProcessingService.saveHomeValues(zillowDataDto);
    }

    public void processAllStorage() throws URISyntaxException, IOException {
        String resourcePath = config.getStoragePath();
        File directory = new File(resourcePath);
        String[] fileNames = directory.list();

        for (String fileName : fileNames) {
            processStorage (resourcePath + fileName);
        }
    }

    public void processRemoteThroughStorage(String url, String storagePath) throws URISyntaxException, IOException {
        fileDownloader.downloadFileToStorage(url, storagePath);
        processStorage (storagePath);
    }

    public void processAllRemoteThroughStorage() throws URISyntaxException, IOException {
        saveAllRemoteToStorage();
        processAllStorage();
    }

    private void saveAllRemoteToStorage() throws URISyntaxException, IOException {
        String storagePath = config.getStoragePath();

        for (String link : config.getLinks()) {
            fileDownloader.downloadFileToStorage(link, storagePath);
        }
    }
}
