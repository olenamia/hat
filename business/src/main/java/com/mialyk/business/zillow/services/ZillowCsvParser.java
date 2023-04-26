package com.mialyk.business.zillow.services;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mialyk.business.zillow.dtos.ZvhiStatesDto;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@Component
public class ZillowCsvParser {
    public List<ZvhiStatesDto> getHomeValues(String content) throws URISyntaxException, IOException {
        StringReader reader = new StringReader(content);

        return getHomeValues(reader);
    }

    public List<ZvhiStatesDto> getHomeValues(Reader reader) {
        CsvToBean<ZvhiStatesDto> csvToBean = new CsvToBeanBuilder<ZvhiStatesDto>(reader)
            .withType(ZvhiStatesDto.class)
            .withSeparator(',')
            .build();
        List<ZvhiStatesDto> housingDataList = csvToBean.parse();

        return housingDataList;
    }

}