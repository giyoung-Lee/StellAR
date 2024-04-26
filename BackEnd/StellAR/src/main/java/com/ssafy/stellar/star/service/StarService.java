package com.ssafy.stellar.star.service;

import com.ssafy.stellar.star.dto.response.StarReturnAllDto;
import com.ssafy.stellar.star.entity.StarEntity;

import java.util.List;

public interface StarService {

    public List<StarReturnAllDto> returnAllStar();
}
