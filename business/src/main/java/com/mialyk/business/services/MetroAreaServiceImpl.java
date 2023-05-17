package com.mialyk.business.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mialyk.business.dtos.MetroAreaDto;
import com.mialyk.persistence.entities.MetroArea;
import com.mialyk.persistence.entities.State;
import com.mialyk.persistence.repositories.MetroAreaRepository;
import com.mialyk.persistence.repositories.StateRepository;

@Service
public class MetroAreaServiceImpl implements MetroAreaService {
    @Autowired
    private StateRepository stateRepository;
    @Autowired
    private MetroAreaRepository metroAreaRepository;

    @Transactional
    @Override
    public MetroArea getOrCreateMetroArea(String regionName, String stateName, int regionId, int sizeRank) {
        MetroArea metroArea;
        Optional <MetroArea> metroAreaOptional = metroAreaRepository.findByRegionId(regionId);
        if (metroAreaOptional.isPresent()) {
            return metroAreaOptional.get();
        }
        else {
            metroArea = new MetroArea();
            metroArea.setRegionName(regionName);
            metroArea.setRegionId(regionId);
            metroArea.setSizeRank(sizeRank);

            Optional<State> state = stateRepository.findByStateName(stateName);
            if (!state.isPresent()) {
                throw new IllegalArgumentException("State " + stateName + " not found");
            }
            metroArea.setState(state.get());
            return metroAreaRepository.save(metroArea);
        }
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

        if (!state.isPresent()) {
            throw new IllegalArgumentException("State " + stateName + " not found");
        }

        List<MetroArea>  metros = metroAreaRepository.findByStateIdOrderByRegionNameAsc(state.get().getId());
        List<MetroAreaDto> metrosDto  = new ArrayList<>();

        for (MetroArea metro : metros) {
            metrosDto.add(new MetroAreaDto(metro));
        }

        return metrosDto;
    }
    @Override
    public Optional<MetroArea> getMetroArea(Integer regionId) {
        Optional <MetroArea> metroAreaOptional = metroAreaRepository.findByRegionId(regionId);
        return metroAreaOptional;
    }
}
