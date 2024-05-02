package com.ssafy.stellar.constellation.service;

import com.ssafy.stellar.constellation.dto.response.ConstellationAllDto;

import java.util.List;
import java.util.Map;

public interface ConstellationService {

    List<ConstellationAllDto> findAllConstellation(String constellationType) throws Exception;

    Map<String, Object> findConstellationLink(String constellationType);
}
