package com.ssafy.stellar.constellation.service;

import com.ssafy.stellar.constellation.dto.response.ConstellationAllDto;
import com.ssafy.stellar.constellation.dto.response.ConstellationLinkDto;

import java.util.List;

public interface ConstellationService {

    List<ConstellationAllDto> findAllConstellation(String constellationType);

    List<ConstellationLinkDto> findConstellationLink(String constellationType);
}
