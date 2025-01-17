package com.ssafy.stellar.constellation.service;

import com.ssafy.stellar.constellation.dto.response.ConstellationDto;
import com.ssafy.stellar.constellation.dto.response.ConstellationEventDto;
import com.ssafy.stellar.constellation.dto.response.ConstellationXODto;

import java.util.List;
import java.util.Map;

public interface ConstellationService {

    List<ConstellationDto> findAllConstellation(String constellationType) throws Exception;

    Map<String, Object> findConstellationLink(String constellationType);

    ConstellationDto findConstellationById(String constellationId) throws Exception;

    List<ConstellationEventDto> returnConstellationEvent();

    List<ConstellationXODto> returnConstellationXO(String ConstellationId);
}
