package com.ssafy.stellar.userConstellation.service;

import com.ssafy.stellar.userConstellation.dto.request.UserConstellationRequestDto;
import com.ssafy.stellar.userConstellation.dto.response.UserConstellationDto;

import java.util.List;

public interface UserConstellationService {

    void manageUserConstellation(UserConstellationRequestDto userConstellationRequestDto, boolean isUpdate);

    List<UserConstellationDto> getUserConstellation(String userId);
    UserConstellationDto getUserConstellationById(String userId, Long userConstellationId);

    void deleteUserConstellation(String userId, Long userConstellationId);
}
