package com.ssafy.stellar.star.service;

import com.ssafy.stellar.star.dto.response.StarDto;
import com.ssafy.stellar.star.dto.response.StarInfoDto;

import java.util.List;

public interface StarService {

    public List<StarDto> returnAllStar();

    public StarInfoDto starInfo(String starId);
}
