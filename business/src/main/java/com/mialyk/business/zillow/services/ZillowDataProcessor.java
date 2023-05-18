package com.mialyk.business.zillow.services;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mialyk.business.common.FileDownloader;
import com.mialyk.business.zillow.configuration.ZillowConfig;
import com.mialyk.business.zillow.model.ZillowHomeValue;

@Component
public class ZillowDataProcessor {
    @Autowired
    private FileDownloader fileDownloader;
    @Autowired
    private ZillowCsvParser csvParser;
    @Autowired
    private ZillowDataStorageService dataProcessingService;
    @Autowired
    private ZillowConfig config;

    public void processUrl (String url) throws URISyntaxException, IOException {
        String content = fileDownloader.getRemoteContent(url);
        List<ZillowHomeValue> zillowDataDto = csvParser.getHomeValues(content);
        dataProcessingService.saveHomeValues(zillowDataDto);
    }

    public void processUrls () throws URISyntaxException, IOException {
        for (String link : config.getLinks()) {
            processUrl(link);
        }
    }

    public void processFile (String storagePath) throws URISyntaxException, IOException {
        Reader fileReader = Files.newBufferedReader(Paths.get(storagePath));

        List<ZillowHomeValue> zillowDataDto = csvParser.getHomeValues(fileReader);
        dataProcessingService.saveHomeValues(zillowDataDto);
    }

    public void processFiles() throws URISyntaxException, IOException {
        String resourcePath = config.getStoragePath();
        File directory = new File(resourcePath);
        FilenameFilter csvFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".csv");
            }
        };
        File[] csvFiles = directory.listFiles(csvFilter);

        for (File csvFile : csvFiles) {
            processFile(csvFile.getAbsolutePath());
        }
    }

    public void saveUrlToStorageAndProcess(String url, String storagePath) throws URISyntaxException, IOException {
        fileDownloader.downloadFileToStorage(url, storagePath);
        processFile (storagePath);
    }

    public void saveUrlsToStorageAndProcess() throws URISyntaxException, IOException {
        saveUrlsToStorage();
        processFiles();
    }

    public void saveUrlsToStorage() throws URISyntaxException, IOException {
        String storagePath = config.getStoragePath();

        for (String link : config.getLinks()) {
            fileDownloader.downloadFileToStorage(link, storagePath);
        }
    }
}
