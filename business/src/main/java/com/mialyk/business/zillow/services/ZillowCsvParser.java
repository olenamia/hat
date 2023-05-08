package com.mialyk.business.zillow.services;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mialyk.business.zillow.dtos.ZillowHomeValueDto;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@Component
public class ZillowCsvParser {
    public List<ZillowHomeValueDto> getHomeValues(String content) throws URISyntaxException, IOException {
        StringReader reader = new StringReader(content);

        return getHomeValues(reader);
    }

    public List<ZillowHomeValueDto> getHomeValues(Reader reader) {
        CsvToBean<ZillowHomeValueDto> csvToBean = new CsvToBeanBuilder<ZillowHomeValueDto>(reader)
            .withType(ZillowHomeValueDto.class)
            .withSeparator(',')
            .build();
        List<ZillowHomeValueDto> housingDataList = csvToBean.parse();

        return housingDataList;
    }

}