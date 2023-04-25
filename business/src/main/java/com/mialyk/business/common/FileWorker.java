package com.mialyk.business.common;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;

@Component
public class FileWorker {

    private InputStream getHttpInputStream(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        URLConnection connection = url.openConnection();
        InputStream inputStream =  connection.getInputStream();
        return inputStream;
    }

    public String getRemoteContent(String urlStr) throws IOException {
        BufferedReader breader = new BufferedReader(getHttpReader(urlStr));
        StringBuilder stringBuilder = new StringBuilder();
    
        String line;
        while((line = breader.readLine()) != null) {
            stringBuilder.append(line);
        }

        String content = stringBuilder.toString();
        return content;
    }

    public Reader getHttpReader(String urlStr) throws IOException {
        Reader reader = new InputStreamReader(getHttpInputStream(urlStr));
        return reader;
    }

    public void downloadFileToStorage(String urlString, String pathToStorage) throws IOException, URISyntaxException, FileNotFoundException {
        InputStream inputStream = getHttpInputStream(urlString);
        ReadableByteChannel readableByteChannel = Channels.newChannel(inputStream);

        URI uri = new URI(urlString);
        String filename = Paths.get(uri.getPath()).getFileName().toString();

        FileOutputStream fileOutputStream = new FileOutputStream(pathToStorage + filename);
        fileOutputStream.getChannel()
            .transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        fileOutputStream.close();
    }

}