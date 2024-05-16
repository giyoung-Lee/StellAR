package com.ssafy.stellar.constellation.service;

import com.ssafy.stellar.constellation.dto.response.ConstellationDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@DisplayName("별자리 서비스 테스트")
public class ConstellationServiceTest {


    @Autowired
    private ConstellationServiceImpl constellationService;

//    @Test
//    @DisplayName("별자리 전체 정보")
//    public void getConstellation() throws Exception {
//        List<ConstellationDto> constellation = constellationService.findAllConstellation("hwangdo13");
//        assertNotNull(constellation);
//    }
//
//    @Test
//    @DisplayName("별자리 연결 정보")
//    public void getConstellationLink() {
//        Map<String, Object> constellation = constellationService.findConstellationLink("3won28su");
//        System.out.println(constellation);
//        assertNotNull(constellation);
//    }
}
