package com.ssafy.stellar.constellation.controller;

import com.dummy.DummyConstellation;
import com.ssafy.stellar.constellation.dto.response.ConstellationAllDto;
import com.ssafy.stellar.constellation.service.ConstellationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ConstellationController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class)
@DisplayName("컨트롤러 테스트")
public class ConstellationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ConstellationController constellationController;

    @MockBean
    ConstellationService constellationService;
    List<ConstellationAllDto> expectedConstellations;

    @BeforeEach
    void setUp() {
//        ReflectionTestUtils.setField();
        expectedConstellations = Arrays.asList(
                DummyConstellation.getConstellationAllDto1(),
                DummyConstellation.getConstellationAllDto2()
        );
    }

    @Test
    @DisplayName("전체 별자리 정보 불러오기 (/constellation/all)")
    void getConstellation() throws Exception {

        given(constellationService.findAllConstellation("hwangdo13")).willReturn(expectedConstellations);

        mockMvc.perform(get("/constellation/all?constellationType=hwangdo13"))
                .andDo(print());
//                .andExpect(status().isOk());

        // story는 너무 길어서... 뺴고...
//        mockMvc.perform(
//                    get("/api/constellation/all?constellationType=hwangdo13"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[0].constellationId", is("Aquarius")))
//                .andExpect(jsonPath("$[0].constellationSeason", is("가을")))
//                .andExpect(jsonPath("$[0].constellationDesc", is("물병자리설명")))
//                .andExpect(jsonPath("$[0].constellationSubName", is("물병자리")))
//                .andExpect(jsonPath("$[0].constellationStartObservation", is("2.16")))
//                .andExpect(jsonPath("$[0].constellationImg", is("http://localhost:8080/api/resources/dump/constellationImg/Aquarius.png")))
////                .andExpect(jsonPath("$[0].constellationStory").exists())
//                .andExpect(jsonPath("$[0].constellationType", is("hwangdo13")))
//                .andExpect(jsonPath("$[0].constellationEndObservation", is("3.11")))
//
//                .andExpect(jsonPath("$[1].constellationId", is("Aries")))
//                .andExpect(jsonPath("$[1].constellationSeason", is("가을")))
//                .andExpect(jsonPath("$[1].constellationDesc", is("양자리설명")))
//                .andExpect(jsonPath("$[1].constellationSubName", is("양자리")))
//                .andExpect(jsonPath("$[1].constellationStartObservation", is("4.19")))
//                .andExpect(jsonPath("$[1].constellationImg", is("http://localhost:8080/api/resources/dump/constellationImg/Aries.png")))
////                .andExpect(jsonPath("$[1].constellationStory").exists())
//                .andExpect(jsonPath("$[1].constellationType", is("hwangdo13")))
//                .andExpect(jsonPath("$[1].constellationEndObservation", is("5.13")));

//        verify(constellationService).findAllConstellation("hwangdo13");
    }
}
