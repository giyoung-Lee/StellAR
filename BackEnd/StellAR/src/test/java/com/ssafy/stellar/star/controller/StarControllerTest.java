package com.ssafy.stellar.star.controller;


import com.google.gson.Gson;
import com.ssafy.stellar.settings.TestSecurityConfig;
import com.ssafy.stellar.star.service.StarServiceImpl;
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

@WebMvcTest(controllers = StarController.class)
@Import(TestSecurityConfig.class)
@ExtendWith(MockitoExtension.class)
@DisplayName("Star Controller Unit-Test")
public class StarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StarServiceImpl starService;

    @Test
    @DisplayName("별 전체 정보 불러오기 - 성공")
    void returnAllStarSuccess() throws Exception {
        String magV = "5.0";
        mockMvc.perform(get("/star/all")
                .param("maxMagv", magV))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("별 전체 정보 불러오기 - 서버 에러")
    void returnAllStarFail() throws Exception {
        when(starService.returnAllStar("5.0")).
                thenThrow(new RuntimeException("Internal server error"));

        String magV = "5.0";
        mockMvc.perform(get("/star/all")
                        .param("maxMagv", magV))
                .andExpect(status().isInternalServerError());
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

    @Test
    @DisplayName("행성 정보 불러오기 - 실패")
    void returnAllPlanetFail() throws Exception {
        when(starService.returnPlanet()).thenThrow(new RuntimeException("Internal server error"));
        mockMvc.perform(get("/star/planet"))
                .andExpect(status().isInternalServerError());
    }

}
