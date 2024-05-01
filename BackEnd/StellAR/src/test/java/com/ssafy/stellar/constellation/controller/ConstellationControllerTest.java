package com.ssafy.stellar.constellation.controller;


import com.ssafy.stellar.constellation.dto.response.ConstellationAllDto;
import com.ssafy.stellar.constellation.service.ConstellationServiceImpl;
import com.ssafy.stellar.settings.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ConstellationController.class)
@DisplayName("컨트롤러 테스트")
public class ConstellationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ConstellationServiceImpl constellationService;

    @Test
    @DisplayName("전체 별자리 정보 불러오기 (/constellation/all)")
    void getConstellation() throws Exception {

        List<ConstellationAllDto> expectedConstellations = Arrays.asList(
                new ConstellationAllDto("Aquarius", "가을", "물병자리설명",
                        "물병자리", "2.16", "Aquarius.png",
                        "그리스 신화에 의하면 물병자리는 독수리에게 납치당해 신들에게 술을 따르는 일을 하게 된 트로이의 왕자 가니메데로 알려져 있다. " +
                                "청춘의 여신 헤베가 신들을 위해 술을 따르는 일을 하고 있었는데, 어느 날 발목을 삐어 그 일을 할 수 없게 되자 제우스는 독수리로 변하여 이다(Ida)산에서" +
                                " 트로이(Troy)의 양떼를 돌보고 있던 미소년 가니메데를 납치해 갔다. 불멸의 컵에 물을 넘쳐흐르도록 가득 채우고 있는 물병자리의 주인공은 바로 미소년 가니메데이다."
                , "hwangdo13", "3.11"),

                new ConstellationAllDto("Aries", "가을", "양자리설명",
                        "양자리", "4.19", "Aries.png",
                        "그리스 신화에 따르면 테살리에 야타마스라고 불리는 왕이 살고 있었는데, " +
                                "그에게는 프릭수스와 헬레라는 두 남매가 있었다. 이들이 어렸을 때 " +
                                "어머니가 돌아가셔서 아이들은 계모의 시달림을 받으며 살았다. " +
                                "이것을 우연히 본 전령의 신 헤르메스는 남매를 불쌍히 여겨 황금 가죽을 가진 숫양을 " +
                                "가지고 내려와 아이들을 보다 행복한 곳으로 보내기 위해 양에 태웠다. 양의 등에 타고 하늘을 날던 중, " +
                                "어린 헬레는 그만 아시아와 유럽을 나누는 해협에 떨어지고 말았다. 훗날 사람들은 " +
                                "헬레의 가여운 운명을 기억하고자 이 해협을 헬레스폰트라 불렀다. 홀로 남은 프릭소스는 " +
                                "양을 타고 계속 날아가 흑해의 동쪽 연안에 자리 잡고 있는 콜키스에 안전하게 도착하였다. " +
                                "제우스는 이 양의 공로를 치하하여 하늘의 별자리로 만들었다.",
                        "hwangdo13", "5.13")
        );

        given(constellationService.findAllConstellation("hwangdo13")).willReturn(expectedConstellations);

        // story는 너무 길어서... 뺴고...
        mockMvc.perform(
                    get("/api/constellation/all?constellationType=hwangdo13"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].constellationId", is("Aquarius")))
                .andExpect(jsonPath("$[0].constellationSeason", is("가을")))
                .andExpect(jsonPath("$[0].constellationDesc", is("물병자리설명")))
                .andExpect(jsonPath("$[0].constellationSubName", is("물병자리")))
                .andExpect(jsonPath("$[0].constellationStartObservation", is("2.16")))
                .andExpect(jsonPath("$[0].constellationImg", is("http://localhost:8080/api/resources/dump/constellationImg/Aquarius.png")))
//                .andExpect(jsonPath("$[0].constellationStory").exists())
                .andExpect(jsonPath("$[0].constellationType", is("hwangdo13")))
                .andExpect(jsonPath("$[0].constellationEndObservation", is("3.11")))

                .andExpect(jsonPath("$[1].constellationId", is("Aries")))
                .andExpect(jsonPath("$[1].constellationSeason", is("가을")))
                .andExpect(jsonPath("$[1].constellationDesc", is("양자리설명")))
                .andExpect(jsonPath("$[1].constellationSubName", is("양자리")))
                .andExpect(jsonPath("$[1].constellationStartObservation", is("4.19")))
                .andExpect(jsonPath("$[1].constellationImg", is("http://localhost:8080/api/resources/dump/constellationImg/Aries.png")))
//                .andExpect(jsonPath("$[1].constellationStory").exists())
                .andExpect(jsonPath("$[1].constellationType", is("hwangdo13")))
                .andExpect(jsonPath("$[1].constellationEndObservation", is("5.13")));

//        verify(constellationService).findAllConstellation("hwangdo13");
    }
}
