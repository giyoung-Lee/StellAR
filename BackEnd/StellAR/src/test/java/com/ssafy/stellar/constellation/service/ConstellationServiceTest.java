package com.ssafy.stellar.constellation.service;

import com.ssafy.stellar.constellation.dto.response.ConstellationAllDto;
import com.ssafy.stellar.constellation.dto.response.ConstellationLinkDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ConstellationServiceTest {
    @Autowired
    private ConstellationService constellationService;

    @Test
    @DisplayName("별자리 전체 정보")
    public void getConstellation() {
        List<ConstellationAllDto> constellation = constellationService.findAllConstellation("3won28su");
        assertNotNull(constellation);
    }

//    @Test
//    @DisplayName("별자리 연결 정보")
//    public void getConstellationLink() {
//        List<ConstellationLinkDto> constellation = constellationService.findConstellationLink("3won28su");
//        assertNotNull(constellation);
//    }
}
