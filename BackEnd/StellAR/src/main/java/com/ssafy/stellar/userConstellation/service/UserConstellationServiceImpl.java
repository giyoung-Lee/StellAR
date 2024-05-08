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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserConstellationServiceImpl implements UserConstellationService {

    private final UserConstellationRepository userConstellationRepository;
    private final UserConstellationLinkRepository userConstellationLinkRepository;
    private final UserRepository userRepository;
    private final StarRepository starRepository;

    public UserConstellationServiceImpl(
            UserConstellationRepository userConstellationRepository,
            UserConstellationLinkRepository userConstellationLinkRepository,
            UserRepository userRepository,
            StarRepository starRepository) {
        this.userConstellationRepository = userConstellationRepository;
        this.userConstellationLinkRepository = userConstellationLinkRepository;
        this.userRepository = userRepository;
        this.starRepository = starRepository;
    }

    @Override
    public void manageUserConstellation(UserConstellationRequestDto userConstellationRequestDto, boolean isUpdate) {
        UserEntity user = validateUser(userConstellationRequestDto.getUserId());
        String name = userConstellationRequestDto.getName();
        Long constellationId = userConstellationRequestDto.getConstellationId();
        List<List<String>> links = userConstellationRequestDto.getLinks();

        UserConstellationEntity userConstellation = userConstellationRepository.findByUserAndUserConstellationId(user, constellationId);
        
        if (!isUpdate && constellationId != null) {
            throw new IllegalStateException("너 데이터 잘못 줬어");
        }

        if (isUpdate && constellationId == null && userConstellation == null) {
            throw new IllegalArgumentException("User Constellation not found for given user and constellationId");
        }

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
        List<UserConstellationEntity> allConstellationByUser = userConstellationRepository.findByUser(user);
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
        for (List<String> link : links) {
            StarEntity startStar = validateStar(link.get(0));
            StarEntity endStar = validateStar(link.get(1));
            UserConstellationLinkEntity userLink = new UserConstellationLinkEntity();
            userLink.setUserConstellation(userConstellation);
            userLink.setStartStar(starRepository.findByStarId(startStar.getStarId()));
            userLink.setEndStar(starRepository.findByStarId(endStar.getStarId()));

            userConstellationLinkRepository.save(userLink);
        }
    }

    private static UserConstellationDto getUserConstellationDto(UserConstellationEntity userConstellation) {
        UserConstellationDto dto = new UserConstellationDto();
        dto.setUserConstellationId(userConstellation.getUserConstellationId());
        dto.setName(userConstellation.getName());
        dto.setDescription(userConstellation.getDescription());
        dto.setCreateTime(userConstellation.getCreateDateTime());
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

}
