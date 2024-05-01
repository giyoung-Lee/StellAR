package com.ssafy.stellar.constellation.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ssafy.stellar.constellation.dto.response.ConstellationAllDto;
import com.ssafy.stellar.constellation.entity.ConstellationEntity;
import com.ssafy.stellar.constellation.entity.ConstellationLinkEntity;
import com.ssafy.stellar.constellation.repository.ConstellationLinkRepository;
import com.ssafy.stellar.constellation.repository.ConstellationRepository;
import com.ssafy.stellar.star.entity.StarEntity;
import com.ssafy.stellar.star.repository.StarRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ConstellationServiceImpl implements ConstellationService{

    private final ConstellationRepository constellationRepository;
    private final StarRepository starRepository;
    private final ConstellationLinkRepository constellationLinkRepository;


    public ConstellationServiceImpl(ConstellationRepository constellationRepository,
                                    StarRepository starRepository,
                                    ConstellationLinkRepository constellationLinkRepository) {
        this.constellationRepository = constellationRepository;
        this.starRepository = starRepository;
        this.constellationLinkRepository = constellationLinkRepository;
    }


    @Override
    public List<ConstellationAllDto> findAllConstellation(String constellationType) throws Exception {

        List<ConstellationEntity> ConstellationEntity =
                constellationRepository.findAllByConstellationType(constellationType);
        List<ConstellationAllDto> dto = new ArrayList<>();

        for(ConstellationEntity entity : ConstellationEntity) {
            ConstellationAllDto temp = getConstellationAllDto(entity);
            dto.add(temp);
        }
        return dto;
    }

    @Transactional
    @Override
    public Map<String, Object> findConstellationLink(String constellationType) {
        List<String> constellList = constellationRepository.findByConstellationType(constellationType);

        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        for (String constellation : constellList) {
            List<ConstellationLinkEntity> list = constellationLinkRepository.findAllByConstellationId(constellation);

            JsonArray jsonArray = new JsonArray();
            for (ConstellationLinkEntity constellation2 : list) {
                JsonArray pairArray = new JsonArray();
                pairArray.add(String.valueOf(constellation2.getStarA().getStarId()));
                pairArray.add(String.valueOf(constellation2.getStarB().getStarId()));
                jsonArray.add(pairArray);
            }
            jsonObject.add(constellation, jsonArray);
        }

        Map<String, Object> map = gson.fromJson(jsonObject, Map.class);
        return map;
    }

    private StarEntity findByStarId (String starId) {
        return starRepository.findByStarId(starId);
    }

    private static ConstellationAllDto getConstellationAllDto(ConstellationEntity entity) throws Exception {
        ConstellationAllDto temp = new ConstellationAllDto();

        temp.setConstellationId(entity.getConstellationId());
        temp.setConstellationSeason(entity.getConstellationSeason());
        temp.setConstellationDesc(entity.getConstellationDesc());
        temp.setConstellationSubName(entity.getConstellationSubName());
        temp.setConstellationStartObservation(entity.getConstellationStartObservation());

        String DIRECTORY = "/resources/dump/constellationImg/";
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(DIRECTORY)
                .path(entity.getConstellationImg())
                .toUriString();

        temp.setConstellationImg(fileDownloadUri);
        temp.setConstellationStory(entity.getConstellationStory());
        temp.setConstellationType(entity.getConstellationType());
        temp.setConstellationEndObservation(entity.getConstellationEndObservation());
        return temp;
    }
}
