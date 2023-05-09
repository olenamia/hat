package com.mialyk.business.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mialyk.business.dtos.MetroAreaDto;
import com.mialyk.persistence.entities.MetroArea;
import com.mialyk.persistence.entities.State;
import com.mialyk.persistence.repositories.MetroAreaRepository;
import com.mialyk.persistence.repositories.StateRepository;

@Service
public class MetroAreaService implements IMetroAreaService {
    @Autowired
    private StateRepository stateRepository;
    @Autowired
    private MetroAreaRepository metroAreaRepository;

    @Override
    public MetroArea getMetroArea(String regionName, String stateName, int regionId, int sizeRank) {
        MetroArea metroArea;
        Optional <MetroArea> metroAreaOptional = metroAreaRepository.findByRegionId(regionId);
        if (metroAreaOptional.isPresent()) {
            metroArea = metroAreaOptional.get();
        }
        else {
            metroArea = new MetroArea();
            metroArea.setRegionName(regionName);
            metroArea.setRegionId(regionId);
            metroArea.setSizeRank(sizeRank);
            Optional<State> state = stateRepository.findByStateName(stateName);
            if (state.isPresent()) {
                metroArea.setState(state.get());
            }
        }
        return metroArea;
    }

    @Override
    public List<MetroAreaDto> getMetroAreaDtos() {
        List<MetroArea> metros = metroAreaRepository.findAll(Sort.by(Sort.Direction.ASC, "regionName"));
        List<MetroAreaDto> metrosDto = new ArrayList<>();
        for (MetroArea metro : metros) {
            metrosDto.add(new MetroAreaDto(metro));
        }
        return metrosDto;
    }

    @Override
    public List<MetroAreaDto> getMetroAreaDtos(String stateName) {
        Optional<State> state = stateRepository.findByRegionName(stateName);
        List<MetroAreaDto> metrosDto  = new ArrayList<>();
        if(state.isPresent()) {
            List<MetroArea>  metros = metroAreaRepository.findByStateIdOrderByRegionNameAsc(state.get().getId());

            for (MetroArea metro : metros) {
                metrosDto.add(new MetroAreaDto(metro));
            }
        }
        return metrosDto;
    }
    @Override
    public Optional<MetroArea> getMetroArea(Integer regionId) {
        Optional <MetroArea> metroAreaOptional = metroAreaRepository.findByRegionId(regionId);
        return metroAreaOptional;
    }
}
