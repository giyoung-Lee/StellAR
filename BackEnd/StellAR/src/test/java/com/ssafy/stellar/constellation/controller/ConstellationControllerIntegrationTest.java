package com.ssafy.stellar.constellation.controller;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("Constellation Controller Integration Test")
@ActiveProfiles("mariadb")
public class ConstellationControllerIntegrationTest {


    @Autowired
    MockMvc mockMvc;

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
    @DisplayName("별자리 연결선 조회 성공")
    void returnConstellationLink() throws Exception {
        String constellationType = "hwangdo13";
        mockMvc.perform(get("/constellation/link")
                        .param("constellationType", constellationType))
                .andExpect(status().isOk());
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
    @DisplayName("천문 이벤트 조회")
    void returnConstellationEvent() throws Exception {
        mockMvc.perform(get("/constellation/event"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("별자리 ox 퀴즈 조회")
    void returnConstellationXo() throws Exception {
        String constellationId = "Aries";

        mockMvc.perform(get("/constellation/xo")
                        .param("constellationId", constellationId))
                .andExpect(status().isOk());
    }

}
