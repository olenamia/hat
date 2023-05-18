package com.mialyk.business.zillow.services;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mialyk.business.zillow.model.ZillowHomeValue;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@Component
public class ZillowCsvParser {
    public List<ZillowHomeValue> getHomeValues(String content) throws URISyntaxException, IOException {
        StringReader reader = new StringReader(content);

        return getHomeValues(reader);
    }

    public List<ZillowHomeValue> getHomeValues(Reader reader) {
        CsvToBean<ZillowHomeValue> csvToBean = new CsvToBeanBuilder<ZillowHomeValue>(reader)
            .withType(ZillowHomeValue.class)
            .withSeparator(',')
            .build();
        List<ZillowHomeValue> housingDataList = csvToBean.parse();

        return housingDataList;
    }

}