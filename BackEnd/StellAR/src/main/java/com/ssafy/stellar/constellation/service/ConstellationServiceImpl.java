package com.ssafy.stellar.constellation.service;

import com.ssafy.stellar.constellation.dto.response.ConstellationAllDto;
import com.ssafy.stellar.constellation.dto.response.ConstellationLinkDto;
import com.ssafy.stellar.constellation.entity.ConstellationEntity;
import com.ssafy.stellar.constellation.repository.ConstellationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConstellationServiceImpl implements ConstellationService{
    
    @Autowired
    private final ConstellationRepository constellationRepository;

    public ConstellationServiceImpl(ConstellationRepository constellationRepository) {
        this.constellationRepository = constellationRepository;
    }


    @Override
    public List<ConstellationAllDto> findAllConstellation(String constellationType) {

        List<ConstellationEntity> ConstellationEntity =
                constellationRepository.findByConstellationType(constellationType);
        List<ConstellationAllDto> dto = new ArrayList<>();

        for(ConstellationEntity entity : ConstellationEntity) {
            ConstellationAllDto temp = getConstellationAllDto(entity);
            dto.add(temp);
        }
        return dto;
    }

    @Override
    public List<ConstellationLinkDto> findConstellationLink(String constellationType) {


        return null;
    }


    private static ConstellationAllDto getConstellationAllDto(ConstellationEntity entity) {
        ConstellationAllDto temp = new ConstellationAllDto();

        temp.setConstellationId(entity.getConstellationId());
        temp.setConstellationSeason(entity.getConstellationSeason());
        temp.setConstellationDesc(entity.getConstellationDesc());
        temp.setConstellationSubName(entity.getConstellationSubName());
        temp.setConstellationStartObservation(entity.getConstellationStartObservation());
        temp.setConstellationImg(entity.getConstellationImg());
        temp.setConstellationStory(entity.getConstellationStory());
        temp.setConstellationType(entity.getConstellationType());
        temp.setConstellationEndObservation(entity.getConstellationEndObservation());
        return temp;
    }
}
