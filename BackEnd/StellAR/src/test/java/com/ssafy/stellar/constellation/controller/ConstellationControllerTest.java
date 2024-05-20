package com.ssafy.stellar.constellation.controller;

import com.ssafy.stellar.constellation.service.ConstellationServiceImpl;
import com.ssafy.stellar.settings.TestSecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = ConstellationController.class)
@Import(TestSecurityConfig.class)
@ExtendWith(MockitoExtension.class)
@DisplayName("Star Controller Unit-Test")
public class ConstellationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ConstellationServiceImpl constellationService;

    @Test
    @DisplayName("별자리 전체 조회(황도 13궁/hwangdo13)")
    void returnZodiacConstellation() throws Exception {
        String constellationType = "hwangdo13";
        mockMvc.perform(get("/constellation/all")
                .param("constellationType", constellationType))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("별자리 전체 조회(3원 28수/3won28su)")
    void return3won28suConstellation() throws Exception {
        String constellationType = "3won28su";
        mockMvc.perform(get("/constellation/all")
                        .param("constellationType", constellationType))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("별자리 전체 조회 실패")
    void returnConstellationFail() throws Exception {
        String constellationType = "hwangdo13";

        when(constellationService.findAllConstellation("hwangdo13"))
                .thenThrow(new RuntimeException("Internal server error"));

        mockMvc.perform(get("/constellation/all")
                        .param("constellationType", constellationType))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("별자리 연결선 조회 성공")
    void returnConstellationLink() throws Exception {
        String constellationType = "hwangdo13";
        mockMvc.perform(get("/constellation/link")
                .param("constellationType", constellationType))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("별자리 연결선 조회 실패")
    void returnConstellationLinkFail() throws Exception {
        String constellationType = "hwangdo13";

        when(constellationService.findConstellationLink(constellationType))
                .thenThrow(new RuntimeException("Internal server error"));

        mockMvc.perform(get("/constellation/link")
                .param("constellationType", constellationType))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("아이디로 별자리 조회 성공")
    void returnConstellationById() throws Exception{
        String constellationId = "Aries";

        mockMvc.perform(get("/constellation/find")
                .param("constellationId", constellationId))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("아이디로 별자리 조회 실패")
    void returnConstellationByIdFail() throws Exception {
        String constellationId = "Aries";

        when(constellationService.findConstellationById(constellationId))
                .thenThrow(new RuntimeException("Internal server error"));

        mockMvc.perform(get("/constellation/find")
                .param("constellationId", constellationId))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("천문 이벤트 조회")
    void returnConstellationEvent() throws Exception {
        mockMvc.perform(get("/constellation/event"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("천문 이벤트 조회 실패")
    void returnConstellationEventFail() throws Exception {
        when(constellationService.returnConstellationEvent())
                .thenThrow(new RuntimeException("Internal server error"));

        mockMvc.perform(get("/constellation/event"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("별자리 ox 퀴즈 조회")
    void returnConstellationXo() throws Exception {
        String constellationId = "Aries";

        mockMvc.perform(get("/constellation/xo")
                .param("constellationId", constellationId))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("별자리 ox 퀴즈 조회 실패")
    void returnConstellationXoFail() throws Exception {
        String constellationId = "Aries";

        when(constellationService.returnConstellationXO(constellationId))
                .thenThrow(new RuntimeException("Internal server error"));

        mockMvc.perform(get("/constellation/xo")
                .param("constellationId", constellationId))
                .andExpect(status().isInternalServerError());
    }

}
