package com.ssafy.stellar.constellation.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ssafy.stellar.constellation.dto.response.ConstellationDto;
import com.ssafy.stellar.constellation.dto.response.ConstellationEventDto;
import com.ssafy.stellar.constellation.dto.response.ConstellationXODto;
import com.ssafy.stellar.constellation.entity.ConstellationEntity;
import com.ssafy.stellar.constellation.entity.ConstellationEventEntity;
import com.ssafy.stellar.constellation.entity.ConstellationLinkEntity;
import com.ssafy.stellar.constellation.entity.ConstellationXOEntity;
import com.ssafy.stellar.constellation.repository.ConstellationEventRepository;
import com.ssafy.stellar.constellation.repository.ConstellationLinkRepository;
import com.ssafy.stellar.constellation.repository.ConstellationRepository;
import com.ssafy.stellar.constellation.repository.ConstellationXORepository;
import com.ssafy.stellar.star.entity.StarEntity;
import com.ssafy.stellar.star.repository.StarRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ConstellationServiceImpl implements ConstellationService{

    private final ConstellationRepository constellationRepository;
    private final ConstellationLinkRepository constellationLinkRepository;
    private final ConstellationEventRepository constellationEventRepository;
    private final ConstellationXORepository constellationXORepository;


    public ConstellationServiceImpl(ConstellationRepository constellationRepository,
                                    ConstellationLinkRepository constellationLinkRepository,
                                    ConstellationEventRepository constellationEventRepository,
                                    ConstellationXORepository constellationXORepository) {
        this.constellationRepository = constellationRepository;
        this.constellationLinkRepository = constellationLinkRepository;
        this.constellationEventRepository = constellationEventRepository;
        this.constellationXORepository = constellationXORepository;
    }


    @Override
    public List<ConstellationDto> findAllConstellation(String constellationType) {

        List<ConstellationEntity> ConstellationEntity =
                constellationRepository.findAllByConstellationType(constellationType);
        List<ConstellationDto> dto = new ArrayList<>();

        for(ConstellationEntity entity : ConstellationEntity) {
            ConstellationDto temp = getConstellationAllDto(entity);
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

    @Override
    public ConstellationDto findConstellationById(String constellationId){
        ConstellationEntity entity = constellationRepository.findAllByConstellationId(constellationId);
        return getConstellationAllDto(entity);
    }

    @Override
    public List<ConstellationEventDto> returnConstellationEvent() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate today = LocalDate.now();
        String formattedDate = today.format(formatter);
        LocalDate startDate = LocalDate.parse(formattedDate, formatter);
        LocalDate endDate = startDate.plusMonths(1);

        List<ConstellationEventEntity> entities = constellationEventRepository.findAllByLocdateBetween(startDate, endDate);

        return entities.stream()
                .map(entity -> new ConstellationEventDto(entity.getAstroEvent(), entity.getAstroTime(), entity.getLocdate()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ConstellationXODto> returnConstellationXO(String ConstellationId) {

        List<ConstellationXOEntity> entity_list = new ArrayList<>();

        if (!ConstellationId.equals("")) {
            entity_list = constellationXORepository.findAllByConstellationId(ConstellationId);
        } else {
            entity_list = constellationXORepository.findAll();
        }

        List<ConstellationXODto> dto_list = new ArrayList<>();

        for (ConstellationXOEntity entity : entity_list) {
            ConstellationXODto dto = new ConstellationXODto();

            dto.setConstellationId(entity.getConstellationId().getConstellationId());
            dto.setConstellationQuestionContents(entity.getConstellationQuestionContents());
            dto.setConstellationQuestionAnswer(entity.getConstellationQuestionAnswer());

            dto_list.add(dto);
        }

        return dto_list;
    }
    // ################################구분선####################################

    protected String buildFileDownloadUri(String fileName) {
        String DIRECTORY = "/resources/dump/constellationImg/";
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(DIRECTORY)
                .path(fileName)
                .toUriString();
    }

    private ConstellationDto getConstellationAllDto(ConstellationEntity entity) {
        ConstellationDto temp = new ConstellationDto();

        temp.setConstellationId(entity.getConstellationId());
        temp.setConstellationSeason(entity.getConstellationSeason());
        temp.setConstellationDesc(entity.getConstellationAlpha());
        temp.setConstellationSubName(entity.getConstellationSubName());
        temp.setConstellationStartObservation(entity.getConstellationStartObservation());

        if (!Objects.equals(entity.getConstellationImg(), "null")) {
            temp.setConstellationImg(buildFileDownloadUri(entity.getConstellationImg()));
        } else {
            temp.setConstellationImg("이미지가 없습니다만..");
        }
        temp.setConstellationStory(entity.getConstellationStory());
        temp.setConstellationType(entity.getConstellationType());
        temp.setConstellationEndObservation(entity.getConstellationEndObservation());
        return temp;
    }
}
