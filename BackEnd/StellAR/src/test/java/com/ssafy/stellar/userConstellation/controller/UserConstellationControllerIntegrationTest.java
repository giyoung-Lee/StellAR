package com.ssafy.stellar.userConstellation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.stellar.userConstellation.dto.request.UserConstellationRequestDto;
import com.ssafy.stellar.userConstellation.entity.UserConstellationEntity;
import com.ssafy.stellar.userConstellation.entity.UserConstellationLinkEntity;
import com.ssafy.stellar.userConstellation.repository.UserConstellationRepository;
import com.ssafy.stellar.userConstellation.repository.UserConstellationLinkRepository;
import com.ssafy.stellar.user.entity.UserEntity;
import com.ssafy.stellar.user.repository.UserRepository;
import com.ssafy.stellar.star.entity.StarEntity;
import com.ssafy.stellar.star.repository.StarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("User Constellation Controller Integration Test")
@ActiveProfiles("mariadb")
public class UserConstellationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserConstellationRepository userConstellationRepository;

    @Autowired
    private UserConstellationLinkRepository userConstellationLinkRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StarRepository starRepository;

    private ObjectMapper objectMapper = new ObjectMapper();
    private UserEntity user;
    private StarEntity star1, star2, star3;

    @BeforeEach
    void setup() {
        user = new UserEntity("constellationIntegrationTester", "password", null, null);
        userRepository.save(user);
        star1 = starRepository.findByStarId("Antares");
        star2 = starRepository.findByStarId("1 Bootes");
        star3 = starRepository.findByStarId("Polaris");
    }

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get("C:/Users/user/Desktop/pjt3/.env"))) {
            stream.filter(line -> !line.startsWith("#") && line.contains("="))
                    .forEach(line -> {
                        String[] keyValue = line.split("=", 2);
                        System.out.println("Setting property: " + keyValue[0] + "=" + keyValue[1]);
                        registry.add(keyValue[0], () -> keyValue[1]);
                    });
        }
    }

    @Test
    @DisplayName("유저 별자리 저장 테스트")
    @WithMockUser(username = "user", password = "password")
    void manageUserConstellationSaveSuccess() throws Exception {
        List<List<String>> links = new ArrayList<>();
        links.add(List.of(star1.getStarId(), star2.getStarId()));
        links.add(List.of(star2.getStarId(), star3.getStarId()));

        UserConstellationRequestDto requestDto = new UserConstellationRequestDto(user.getUserId(), null, "My Constellation", "Description", links);
        String requestDtoJson = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(post("/user-constellation/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestDtoJson))
                .andExpect(status().isCreated());

        List<UserConstellationEntity> constellations = userConstellationRepository.findByUserOrderByCreateDateTimeDesc(user);
        assertThat(constellations).hasSize(1);
        assertThat(constellations.get(0).getName()).isEqualTo("My Constellation");
    }

    @Test
    @DisplayName("유저 별자리 수정 테스트")
    @WithMockUser(username = "user", password = "password")
    void manageUserConstellationUpdateSuccess() throws Exception {
        UserConstellationEntity constellation = new UserConstellationEntity();
        constellation.setUser(user);
        constellation.setName("Old Constellation");
        constellation.setDescription("Old Description");
        userConstellationRepository.save(constellation);

        List<List<String>> links = new ArrayList<>();
        links.add(List.of(star1.getStarId(), star2.getStarId()));
        links.add(List.of(star2.getStarId(), star3.getStarId()));

        UserConstellationRequestDto requestDto = new UserConstellationRequestDto(user.getUserId(), constellation.getUserConstellationId(), "Updated Constellation", "Updated Description", links);
        String requestDtoJson = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(put("/user-constellation/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestDtoJson))
                .andExpect(status().isOk());

        List<UserConstellationEntity> constellations = userConstellationRepository.findByUserOrderByCreateDateTimeDesc(user);
        assertThat(constellations).hasSize(1);
        assertThat(constellations.get(0).getName()).isEqualTo("Updated Constellation");
    }

    @Test
    @DisplayName("유저 별자리 개별 조회 성공 테스트")
    @WithMockUser(username = "user", password = "password")
    void getUserConstellationByIdSuccess() throws Exception {
        UserConstellationEntity constellation = new UserConstellationEntity();
        constellation.setUser(user);
        constellation.setName("My Constellation");
        constellation.setDescription("Description");
        userConstellationRepository.save(constellation);
        UserConstellationLinkEntity userConstellationLink = new UserConstellationLinkEntity(null, constellation, star1, star2);
        userConstellationLinkRepository.save(userConstellationLink);

        Long constellationId = userConstellationRepository.findByUser(user).get(0).getUserConstellationId();

        mockMvc.perform(get("/user-constellation")
                        .param("userId", user.getUserId())
                        .param("constellationId", String.valueOf(constellationId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("My Constellation"));
    }


    @Test
    @DisplayName("유저 별자리 전체 조회 성공 테스트")
    @WithMockUser(username = "user", password = "password")
    void getUserConstellationSuccess() throws Exception {
        UserConstellationEntity constellation1 = new UserConstellationEntity();
        constellation1.setUser(user);
        constellation1.setName("Constellation 1");
        constellation1.setDescription("Description 1");
        userConstellationRepository.save(constellation1);

        UserConstellationEntity constellation2 = new UserConstellationEntity();
        constellation2.setUser(user);
        constellation2.setName("Constellation 2");
        constellation2.setDescription("Description 2");
        userConstellationRepository.save(constellation2);
        UserConstellationLinkEntity userConstellationLink1 = new UserConstellationLinkEntity(null, constellation1, star1, star2);
        userConstellationLinkRepository.save(userConstellationLink1);
        UserConstellationLinkEntity userConstellationLink2 = new UserConstellationLinkEntity(null, constellation2, star1, star2);
        userConstellationLinkRepository.save(userConstellationLink2);

        mockMvc.perform(get("/user-constellation/all")
                        .param("userId", user.getUserId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("Constellation 1"));
    }

    @Test
    @DisplayName("유저 별자리 삭제 성공 테스트")
    @WithMockUser(username = "user", password = "password")
    void deleteUserConstellationSuccess() throws Exception {
        UserConstellationEntity constellation = new UserConstellationEntity();
        constellation.setUser(user);
        constellation.setName("My Constellation");
        constellation.setDescription("Description");
        userConstellationRepository.save(constellation);

        mockMvc.perform(delete("/user-constellation/delete")
                        .param("userId", user.getUserId())
                        .param("constellationId", String.valueOf(constellation.getUserConstellationId())))
                .andExpect(status().isNoContent());

        List<UserConstellationEntity> constellations = userConstellationRepository.findByUserOrderByCreateDateTimeDesc(user);
        assertThat(constellations).isEmpty();
    }

    // 링크 조회 성공 테스트
    @Test
    @DisplayName("유저 별자리 링크 조회 성공 테스트")
    @WithMockUser(username = "user", password = "password")
    void getUserConstellationLinksSuccessTest() throws Exception {
        UserConstellationEntity constellation = new UserConstellationEntity();
        constellation.setUser(user);
        constellation.setName("My Constellation");
        constellation.setDescription("Description");
        userConstellationRepository.save(constellation);
        UserConstellationLinkEntity link1 = new UserConstellationLinkEntity(null, constellation, star1, star2);
        UserConstellationLinkEntity link2 = new UserConstellationLinkEntity(null, constellation, star2, star3);
        userConstellationLinkRepository.save(link1);
        userConstellationLinkRepository.save(link2);
        String constellationId = String.valueOf(userConstellationRepository.findByUser(user).get(0).getUserConstellationId());

        mockMvc.perform(get("/user-constellation/link")
                        .param("userId", user.getUserId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$." + constellationId + ".length()").value(2))
                .andExpect(jsonPath("$." + constellationId + "[0][0]").value(star1.getStarId()))
                .andExpect(jsonPath("$." + constellationId + "[0][1]").value(star2.getStarId()))
                .andExpect(jsonPath("$." + constellationId + "[1][0]").value(star2.getStarId()))
                .andExpect(jsonPath("$." + constellationId + "[1][1]").value(star3.getStarId()));
    }

    @Test
    @DisplayName("유저 별자리 링크 조회 실패 테스트 - 잘못된 별자리 ID")
    @WithMockUser(username = "user", password = "password")
    void getUserConstellationLinksFailureTest() throws Exception {
        mockMvc.perform(get("/user-constellation/link")
                        .param("userId", user.getUserId())
                        .param("constellationId", "9999"))
                .andExpect(status().isBadRequest());
    }

    // 실패 테스트 추가
    @Test
    @DisplayName("유저 별자리 저장 실패 테스트 - 이미 있는 별자리")
    @WithMockUser(username = "user", password = "password")
    void manageUserConstellationSaveFailure() throws Exception {
        UserConstellationEntity constellation = new UserConstellationEntity();
        constellation.setUser(user);
        constellation.setName("My Constellation");
        constellation.setDescription("Description");
        userConstellationRepository.save(constellation);

        List<List<String>> links = new ArrayList<>();
        links.add(List.of(star1.getStarId(), star2.getStarId()));
        links.add(List.of(star2.getStarId(), star3.getStarId()));

        UserConstellationRequestDto requestDto = new UserConstellationRequestDto(user.getUserId(), constellation.getUserConstellationId(), "My Constellation", "Description", links);
        String requestDtoJson = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(post("/user-constellation/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestDtoJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("유저 별자리 수정 실패 테스트 - 별자리 없음")
    @WithMockUser(username = "user", password = "password")
    void manageUserConstellationUpdateFailure() throws Exception {
        List<List<String>> links = new ArrayList<>();
        links.add(List.of(star1.getStarId(), star2.getStarId()));
        links.add(List.of(star2.getStarId(), star3.getStarId()));

        UserConstellationRequestDto requestDto = new UserConstellationRequestDto(user.getUserId(), 999L, "Updated Constellation", "Updated Description", links);
        String requestDtoJson = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(put("/user-constellation/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestDtoJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("유저 별자리 개별 조회 실패 테스트 - 잘못된 데이터")
    @WithMockUser(username = "user", password = "password")
    void getUserConstellationByIdFailure() throws Exception {
        mockMvc.perform(get("/user-constellation")
                        .param("userId", user.getUserId())
                        .param("constellationId", "invalidConstellationId"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("유저 별자리 전체 조회 실패 테스트 - 유저 없음")
    @WithMockUser(username = "user", password = "password")
    void getUserConstellationFailure() throws Exception {
        mockMvc.perform(get("/user-constellation/all")
                        .param("userId", "invalidUserId"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("유저 별자리 삭제 실패 테스트 - 별자리 없음")
    @WithMockUser(username = "user", password = "password")
    void deleteUserConstellationFailure() throws Exception {
        mockMvc.perform(delete("/user-constellation/delete")
                        .param("userId", user.getUserId())
                        .param("constellationId", "invalidConstellationId"))
                .andExpect(status().isBadRequest());
    }
}
