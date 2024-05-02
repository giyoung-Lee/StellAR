package com.ssafy.stellar.userConstellation.service;

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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
        List<List<String>> links = userConstellationRequestDto.getLinks();
        UserConstellationEntity userConstellation = userConstellationRepository.findByUserAndName(user, name);
        
        if (!isUpdate && userConstellation != null) {
            throw new IllegalStateException("User Constellation already exists for given user and star");
        }

        if (isUpdate && userConstellation == null) {
            throw new IllegalArgumentException("User Constellation not found for given user and star");
        }

        userConstellationLinkRepository.deleteByUserConstellation(userConstellation);
        userConstellation = userConstellation == null ? new UserConstellationEntity() : userConstellation;
        userConstellation.setUser(user);
        userConstellation.setName(name);
        userConstellation.setDescription(userConstellationRequestDto.getDescription());

        userConstellationRepository.save(userConstellation);

        System.out.println("links = " + links);

        for (List<String> link : links) {
            System.out.println("link = " + link);
            StarEntity startStar = validateStar(link.get(0));  // 추가된 유효성 검사
            StarEntity endStar = validateStar(link.get(1));    // 추가된 유효성 검사
            UserConstellationLinkEntity userLink = new UserConstellationLinkEntity();
            userLink.setUserConstellation(userConstellation);
            userLink.setStartStar(starRepository.findByStarId(startStar.getStarId()));
            userLink.setEndStar(starRepository.findByStarId(endStar.getStarId()));

            userConstellationLinkRepository.save(userLink);
        }


    }

    @Override
    public List<UserConstellationDto> getUserConstellation(String userId) {
        UserEntity user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with id: " + userId);
        }
        List<UserConstellationEntity> allConstellationByUser = userConstellationRepository.findByUser(user);
        List<UserConstellationDto> userConstellationDto = new ArrayList<>();

        for (UserConstellationEntity userConstellation : allConstellationByUser) {
            UserConstellationDto dto = new UserConstellationDto();

            dto.setName(userConstellation.getName());
            dto.setDescription(userConstellation.getDescription());
            dto.setCreateTime(userConstellation.getCreateDateTime());

            List<UserConstellationLinkEntity> linksByUserConstellationId = userConstellationLinkRepository.findByUserConstellation(userConstellation);
            List<UserConstellationLinkDto> userLinks = new ArrayList<>();

            for (UserConstellationLinkEntity link : linksByUserConstellationId) {
                UserConstellationLinkDto userConstellationLink = new UserConstellationLinkDto();
                
                userConstellationLink.setUserConstellationId(userConstellation.getUserConstellationId());
                userConstellationLink.setStartStar(link.getStartStar().getStarId());
                userConstellationLink.setEndStar(link.getEndStar().getStarId());

                userLinks.add(userConstellationLink);
            }
            dto.setLinks(userLinks);
            userConstellationDto.add(dto);
        }

        return userConstellationDto;
    }

    @Transactional
    @Override
    public void deleteUserConstellation(String userId, String constellationName) {
        UserEntity user = validateUser(userId);
        UserConstellationEntity userConstellation = userConstellationRepository.findByUserAndName(user, constellationName);


        if (userConstellation == null) {
            throw new IllegalArgumentException("User Constellation not found for given user and star");
        }

        userConstellationLinkRepository.deleteByUserConstellation(userConstellation);
        userConstellationRepository.delete(userConstellation);

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

}
