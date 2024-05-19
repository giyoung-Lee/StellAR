package com.ssafy.stellar.userConstellation.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ssafy.stellar.star.entity.StarEntity;
import com.ssafy.stellar.star.repository.StarRepository;
import com.ssafy.stellar.user.entity.UserEntity;
import com.ssafy.stellar.user.repository.UserRepository;
import com.ssafy.stellar.userConstellation.dto.request.UserConstellationRequestDto;
import com.ssafy.stellar.userConstellation.dto.response.UserConstellationDto;
import com.ssafy.stellar.userConstellation.dto.response.UserConstellationLinkDto;
import com.ssafy.stellar.userConstellation.entity.UserConstellationEntity;
import com.ssafy.stellar.userConstellation.entity.UserConstellationLinkEntity;
import com.ssafy.stellar.userConstellation.repository.UserConstellationLinkRepository;
import com.ssafy.stellar.userConstellation.repository.UserConstellationRepository;
import com.ssafy.stellar.utils.stars.CalcStarLocation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class UserConstellationServiceImpl implements UserConstellationService {

    private final UserConstellationRepository userConstellationRepository;
    private final UserConstellationLinkRepository userConstellationLinkRepository;
    private final UserRepository userRepository;
    private final StarRepository starRepository;
    private final CalcStarLocation calc;

    @Value("${stellar.star.max-magv}")
    private Double starMaxMagv;

    @Value("${stellar.star.min-magv}")
    private Double starMinMagv;

    public UserConstellationServiceImpl(
            UserConstellationRepository userConstellationRepository,
            UserConstellationLinkRepository userConstellationLinkRepository,
            UserRepository userRepository,
            StarRepository starRepository,
            CalcStarLocation calc) {
        this.userConstellationRepository = userConstellationRepository;
        this.userConstellationLinkRepository = userConstellationLinkRepository;
        this.userRepository = userRepository;
        this.starRepository = starRepository;
        this.calc = calc;
    }

    @Transactional
    @Override
    public void manageUserConstellation(UserConstellationRequestDto userConstellationRequestDto, boolean isUpdate) {
        UserEntity user = validateUser(userConstellationRequestDto.getUserId());
        String name = userConstellationRequestDto.getName();
        Long constellationId = userConstellationRequestDto.getConstellationId();
        List<List<String>> links = userConstellationRequestDto.getLinks();

        UserConstellationEntity userConstellation = userConstellationRepository.findByUserAndUserConstellationId(user, constellationId);
        
        if (!isUpdate && constellationId != null) {
            throw new IllegalStateException("Creating constellation don't need constellation Id.");
        }

        if (isUpdate && (constellationId == null || userConstellation == null)) {
            throw new IllegalArgumentException("User Constellation not found for given user and constellationId");
        }

        // 링크 데이터 확인
        validateLinks(links);

        userConstellationLinkRepository.deleteByUserConstellation(userConstellation);
        userConstellation = userConstellation == null ? new UserConstellationEntity() : userConstellation;
        userConstellation.setUser(user);
        userConstellation.setName(name);
        userConstellation.setDescription(userConstellationRequestDto.getDescription());

        userConstellationRepository.save(userConstellation);

        saveUserConstellationLinks(links, userConstellation);
    }

    @Override
    public List<UserConstellationDto> getUserConstellation(String userId) {
        UserEntity user = validateUser(userId);
        List<UserConstellationEntity> allConstellationByUser = userConstellationRepository.findByUserOrderByCreateDateTimeDesc(user);
        List<UserConstellationDto> userConstellationDto = new ArrayList<>();

        for (UserConstellationEntity userConstellation : allConstellationByUser) {
            UserConstellationDto dto = getUserConstellationDto(userConstellation);

            List<UserConstellationLinkEntity> linksByUserConstellationId = userConstellationLinkRepository.findByUserConstellation(userConstellation);
            List<UserConstellationLinkDto> userLinks = getUserConstellationLinkDtos(userConstellation, linksByUserConstellationId);
            dto.setLinks(userLinks);
            userConstellationDto.add(dto);
        }

        return userConstellationDto;
    }

    @Override
    public UserConstellationDto getUserConstellationById(String userId, Long userConstellationId) {
        UserEntity user = validateUser(userId);
        UserConstellationEntity userConstellation = validateUserConstellation(userConstellationId, user);

        return getUserConstellationDto(userConstellation);
    }

    @Transactional
    @Override
    public void deleteUserConstellation(String userId, Long userConstellationId) {
        UserEntity user = validateUser(userId);
        UserConstellationEntity userConstellation = validateUserConstellation(userConstellationId, user);

        userConstellationLinkRepository.deleteByUserConstellation(userConstellation);
        userConstellationRepository.delete(userConstellation);

    }

    @Override
    public Map<String, Object> getUserConstellationLink(String userId) {
        List<Long> userConstellationList = userConstellationRepository.findUserConstellationIdsByUserId(userId);
        if (userConstellationList == null | userConstellationList.isEmpty()) {
            throw new IllegalArgumentException("user don't have any constellations.");
        }
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        for(Long constellationId : userConstellationList) {
            List<UserConstellationLinkEntity> temp =
                    userConstellationLinkRepository.findByUserConstellationId(constellationId);
            JsonArray jsonArray = new JsonArray();
            for(UserConstellationLinkEntity entity : temp) {
                JsonArray pairArray = new JsonArray();
                pairArray.add(entity.getStartStar().getStarId());
                pairArray.add(entity.getEndStar().getStarId());
                jsonArray.add(pairArray);
            }
            jsonObject.add(constellationId.toString(), jsonArray);
        }
        Map<String, Object> map = gson.fromJson(jsonObject, Map.class);
        return map;
    }

    private void saveUserConstellationLinks(List<List<String>> links, UserConstellationEntity userConstellation) {
        List<UserConstellationLinkEntity> userConstellationLinkEntities = new ArrayList<>();

        for (List<String> link : links) {
            StarEntity startStar = validateStar(link.get(0));
            StarEntity endStar = validateStar(link.get(1));
            if (startStar.equals(endStar)) {
                continue;
            }

            UserConstellationLinkEntity userLink = new UserConstellationLinkEntity();
            userLink.setUserConstellation(userConstellation);
            userLink.setStartStar(startStar);
            userLink.setEndStar(endStar);

            userConstellationLinkEntities.add(userLink);
        }

        userConstellationLinkRepository.saveAll(userConstellationLinkEntities);
    }

    private UserConstellationDto getUserConstellationDto(UserConstellationEntity userConstellation) {
        List<UserConstellationLinkEntity> userConstellationLinks =  userConstellationLinkRepository.findByUserConstellation(userConstellation);

        StarEntity star = starRepository.findByStarId(userConstellationLinks.get(0).getStartStar().getStarId());
        double degreeDEC = calc.calculateNewDec(star.getDeclination(), Double.parseDouble(star.getPMDEC()));
        double hourRA = calc.calculateNewRA(star.getRA(), Double.parseDouble(star.getPMRA()));
        double normalizedMagV = calc.calculateNormalizedMagV(star);

        UserConstellationDto dto = new UserConstellationDto();
        dto.setUserConstellationId(userConstellation.getUserConstellationId());
        dto.setName(userConstellation.getName());
        dto.setDescription(userConstellation.getDescription());
        dto.setCreateTime(userConstellation.getCreateDateTime());
        dto.setDegreeDEC(degreeDEC);
        dto.setHourRA(hourRA);
        dto.setNomalizedMagV(normalizedMagV);
        return dto;
    }

    private static List<UserConstellationLinkDto> getUserConstellationLinkDtos(UserConstellationEntity userConstellation, List<UserConstellationLinkEntity> linksByUserConstellationId) {
        List<UserConstellationLinkDto> userLinks = new ArrayList<>();

        for (UserConstellationLinkEntity link : linksByUserConstellationId) {
            UserConstellationLinkDto userConstellationLink = new UserConstellationLinkDto();

            userConstellationLink.setUserConstellationId(userConstellation.getUserConstellationId());
            userConstellationLink.setStartStar(link.getStartStar().getStarId());
            userConstellationLink.setEndStar(link.getEndStar().getStarId());

            userLinks.add(userConstellationLink);
        }
        return userLinks;
    }

    private UserEntity validateUser(String userId) {
        UserEntity user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found with id: " + userId);
        }
        return user;
    }

    private StarEntity validateStar(String starId) {
        StarEntity star = starRepository.findByStarId(starId);
        if (star == null) {
            throw new IllegalArgumentException("Star not found with id: " + starId);
        }
        return star;
    }

    private UserConstellationEntity validateUserConstellation(Long constellationId, UserEntity user) {
        UserConstellationEntity userConstellation = userConstellationRepository.findByUserAndUserConstellationId(user, constellationId);

        if (userConstellation == null) {
            throw new IllegalArgumentException("User Constellation not found for given user and userConstellationId");
        }
        return userConstellation;
    }

    public void validateLinks(List<List<String>> links) {
        // links가 null이거나 비어 있는지 확인
        if (Objects.isNull(links) || links.isEmpty()) {
            throw new IllegalArgumentException("Links list cannot be null or empty");
        }

        // 각 리스트의 길이가 2인지 확인
        for (List<String> link : links) {
            if (Objects.isNull(link) || link.size() != 2) {
                throw new IllegalArgumentException("Each link must contain exactly 2 elements");
            }
        }
    }

}
