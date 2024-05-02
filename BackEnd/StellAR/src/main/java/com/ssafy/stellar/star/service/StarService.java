package com.ssafy.stellar.star.service;


import com.ssafy.stellar.star.dto.response.PlanetDto;

import java.util.List;
import java.util.Map;

public interface StarService {

    public Map<String, Object> returnAllStar();

    public List<PlanetDto> returnPlanet();
}
