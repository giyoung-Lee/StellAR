package com.ssafy.stellar.userConstellation.service;

import com.ssafy.stellar.userConstellation.dto.request.UserConstellationRequestDto;
import com.ssafy.stellar.userConstellation.dto.response.UserConstellationDto;

import java.util.List;

public interface UserConstellationService {

    void manageUserConstellation(UserConstellationRequestDto userConstellationRequestDto, boolean isUpdate);

    List<UserConstellationDto> getUserConstellation(String userId);

    void deleteUserConstellation(String userId, String constellationName);
}
