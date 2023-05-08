package com.mialyk.business.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

public interface IFileDownloader {

    String getRemoteContent(String urlStr) throws IOException;

    void downloadFileToStorage(String urlString, String pathToStorage)
            throws IOException, URISyntaxException, FileNotFoundException;

}