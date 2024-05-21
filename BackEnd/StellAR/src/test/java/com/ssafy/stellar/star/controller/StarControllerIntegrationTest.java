package com.ssafy.stellar.star.controller;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("Star Controller Integration Test")
@ActiveProfiles("mariadb")
public class StarControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("별 전체 정보 불러오기 - 성공")
    void returnAllStarSuccess() throws Exception {
        String magV = "5.0";
        mockMvc.perform(get("/star/all")
                        .param("maxMagv", magV))
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("별 전체 정보 불러오기 - Bad Request")
    void returnAllStarBadRequest() throws Exception {
        mockMvc.perform(get("/star/all"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("행성 정보 불러오기 - 성공")
    void returnAllPlanetSuccess() throws Exception {
        mockMvc.perform(get("/star/planet"))
                .andExpect(status().isOk());
    }

}
