package com.ssafy.stellar.userConstellation.service;

import com.ssafy.stellar.star.entity.StarEntity;
import com.ssafy.stellar.star.repository.StarRepository;
import com.ssafy.stellar.user.entity.UserEntity;
import com.ssafy.stellar.user.repository.UserRepository;
import com.ssafy.stellar.userBookmark.entity.UserBookmarkEntity;
import com.ssafy.stellar.userConstellation.dto.request.UserConstellationRequestDto;
import com.ssafy.stellar.userConstellation.dto.response.UserConstellationDto;
import com.ssafy.stellar.userConstellation.entity.UserConstellationEntity;
import com.ssafy.stellar.userConstellation.entity.UserConstellationLinkEntity;
import com.ssafy.stellar.userConstellation.repository.UserConstellationLinkRepository;
import com.ssafy.stellar.userConstellation.repository.UserConstellationRepository;
import com.ssafy.stellar.utils.stars.CalcStarLocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DisplayName("User Constellation Service Unit-Test")
public class UserConstellationServiceTest {

    @InjectMocks
    private UserConstellationServiceImpl userConstellationService;

    @Mock
    private UserConstellationRepository userConstellationRepository;

    @Mock
    private UserConstellationLinkRepository userConstellationLinkRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private StarRepository starRepository;

    @Mock
    private CalcStarLocation calc;

    UserEntity user;
    StarEntity star1, star2, star3;

    List<List<String>> links;

    @BeforeEach
    void setUp() {
        user = new UserEntity();
        user.setUserId("wncks");
        star1 = createStarEntity("star1");
        star2 = createStarEntity("star2");
        star3 = createStarEntity("star3");

        links = new ArrayList<>();

        lenient().when(userRepository.findByUserId("wncks")).thenReturn(user);
        lenient().when(starRepository.findByStarId("star1")).thenReturn(star1);
        lenient().when(starRepository.findByStarId("star2")).thenReturn(star2);
        lenient().when(starRepository.findByStarId("star3")).thenReturn(star3);
        lenient().when(calc.calculateNewRA(anyString(), anyDouble())).thenReturn(0.0);
        lenient().when(calc.calculateNewDec(anyString(), anyDouble())).thenReturn(0.0);
    }

    private StarEntity createStarEntity(String starId) {
        StarEntity star = new StarEntity();
        star.setStarId(starId);
        star.setRA("0 0 0");
        star.setDeclination("+0 0 0");
        star.setPMRA("0.0");
        star.setPMDEC("0.0");
        return star;
    }

    @Test
    @DisplayName("유저 별자리 생성 성공 테스트")
    void createUserConstellationSuccessTest() {
        // Given
        links.add(createLink(star1.getStarId(), star2.getStarId()));
        links.add(createLink(star2.getStarId(), star2.getStarId()));
        links.add(createLink(star2.getStarId(), star3.getStarId()));

        String userConstellationName = "Constellation Test";
        String userConstellationDescription = "Constellation Description";

        UserConstellationRequestDto request = new UserConstellationRequestDto(user.getUserId(), null, userConstellationName, userConstellationDescription, links);
        UserConstellationEntity userConstellation = createUserConstellation(user, userConstellationName, userConstellationDescription);

        List<UserConstellationLinkEntity> savedLinks = createLinks(userConstellation, links);

        when(userConstellationRepository.save(any(UserConstellationEntity.class))).thenReturn(userConstellation);
        when(userConstellationLinkRepository.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        userConstellationService.manageUserConstellation(request, false);

        // Then
        verifyUserConstellationSaved(user, userConstellationName);
        verifyLinksSaved(userConstellation, savedLinks);
    }

    @Test
    @DisplayName("유저 별자리 생성 실패 테스트 - 생성시 요청 데이터에 잘못된 데이터를 보냄")
    void createUserConstellationFailTest() {
        links.add(createLink(star1.getStarId(), star2.getStarId()));
        links.add(createLink(star2.getStarId(), star3.getStarId()));

        String userConstellationName = "Constellation Test";
        String userConstellationDescription = "Constellation Description";

        UserConstellationRequestDto request = new UserConstellationRequestDto(user.getUserId(), 1L, userConstellationName, userConstellationDescription, links);

        // when & then
        assertThatThrownBy(() -> {
            userConstellationService.manageUserConstellation(request, false);
        }).isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Creating constellation don't need constellation Id.");
    }

    @Test
    @DisplayName("유저 별자리 생성 실패 테스트 - 링크가 비어있음")
    void createUserConstellationFailTest2() {
        links.add(createLink(star1.getStarId(), star2.getStarId()));
        links.add(createLink(star2.getStarId(), star3.getStarId()));

        String userConstellationName = "Constellation Test";
        String userConstellationDescription = "Constellation Description";

        UserConstellationRequestDto request = new UserConstellationRequestDto(user.getUserId(), null, userConstellationName, userConstellationDescription, new ArrayList<>());

        // when & then
        assertThatThrownBy(() -> {
            userConstellationService.manageUserConstellation(request, false);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Links list cannot be null or empty");
    }

    @Test
    @DisplayName("유저 별자리 생성 실패 테스트 - 링크 데이터가 제대로 오지 않았음")
    void createUserConstellationFailTest3() {
        // Given
        links.add(createLink(star1.getStarId(), star2.getStarId()));
        links.add(createLink(star2.getStarId(), star3.getStarId()));
        List<List<String>> wrongLinks = new ArrayList<>();
        List<String> wrongLink = new ArrayList<>();
        wrongLink.add("star1");
        wrongLinks.add(wrongLink);
        String userConstellationName = "Constellation Test";
        String userConstellationDescription = "Constellation Description";

        UserConstellationRequestDto request = new UserConstellationRequestDto(user.getUserId(), null, userConstellationName, userConstellationDescription, wrongLinks);

        // When & Then
        assertThatThrownBy(() -> {
            userConstellationService.manageUserConstellation(request, false);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Each link must contain exactly 2 elements");
    }

    @Test
    @DisplayName("유저 별자리 생성 실패 테스트 - 링크된 별이 없는 별임")
    void createUserConstellationFailTest4() {
        // Given
        links.add(createLink(star1.getStarId(), star2.getStarId()));
        links.add(createLink(star2.getStarId(), star3.getStarId()));
        List<List<String>> wrongLinks = new ArrayList<>();
        List<String> wrongLink = new ArrayList<>();
        wrongLink.add("star1");
        wrongLink.add("star4");
        wrongLinks.add(wrongLink);
        String userConstellationName = "Constellation Test";
        String userConstellationDescription = "Constellation Description";

        UserConstellationRequestDto request = new UserConstellationRequestDto(user.getUserId(), null, userConstellationName, userConstellationDescription, wrongLinks);

        // When & Then
        lenient().when(starRepository.findByStarId("star4")).thenReturn(null);
        assertThatThrownBy(() -> {
            userConstellationService.manageUserConstellation(request, false);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Star not found with id: " + "star4");
    }

    @Test
    @DisplayName("유저 별자리 수정 성공 테스트")
    void updateUserConstellationSuccessTest() {
        // Given
        links.add(createLink(star1.getStarId(), star2.getStarId()));
        links.add(createLink(star2.getStarId(), star3.getStarId()));

        String originalName = "Constellation Test";
        String originalDescription = "Constellation Description";

        String newName = "New Constellation Name";
        String newConstellationDescription = "New Description";
        UserConstellationEntity originalUserConstellation = createUserConstellation(user, originalName, originalDescription);
        originalUserConstellation.setUserConstellationId(1L);

        UserConstellationRequestDto request = new UserConstellationRequestDto(user.getUserId(), 1L, newName, newConstellationDescription, links);

        List<UserConstellationLinkEntity> savedLinks = createLinks(originalUserConstellation, links);

        when(userConstellationRepository.findByUserAndUserConstellationId(user, 1L)).thenReturn(originalUserConstellation);
        when(userConstellationRepository.save(any(UserConstellationEntity.class))).thenReturn(originalUserConstellation);
        when(userConstellationLinkRepository.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        userConstellationService.manageUserConstellation(request, true);

        // Then
        verifyUserConstellationSaved(user, newName);
        verifyLinksSaved(originalUserConstellation, savedLinks);
    }

    @Test
    @DisplayName("유저 별자리 수정 실패 테스트 - user Constellation ID 없음")
    void updateUserConstellationFailTest() {
        links.add(createLink(star1.getStarId(), star2.getStarId()));
        links.add(createLink(star2.getStarId(), star3.getStarId()));

        String originalName = "Constellation Test";
        String originalDescription = "Constellation Description";

        String newName = "New Constellation Name";
        String newConstellationDescription = "New Description";
        UserConstellationEntity originalUserConstellation = createUserConstellation(user, originalName, originalDescription);
        originalUserConstellation.setUserConstellationId(1L);

        UserConstellationRequestDto request = new UserConstellationRequestDto(user.getUserId(), null, newName, newConstellationDescription, links);
        List<UserConstellationLinkEntity> savedLinks = createLinks(originalUserConstellation, links);

        // when & then
        assertThatThrownBy(() -> {
            userConstellationService.manageUserConstellation(request, true);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("User Constellation not found for given user and constellationId");
    }

    @Test
    @DisplayName("유저 별자리 수정 실패 테스트 - 수정하려는 user Constellation이 없음")
    void updateUserConstellationFailTest2() {
        links.add(createLink(star1.getStarId(), star2.getStarId()));
        links.add(createLink(star2.getStarId(), star3.getStarId()));

        String originalName = "Constellation Test";
        String originalDescription = "Constellation Description";

        String newName = "New Constellation Name";
        String newConstellationDescription = "New Description";
        UserConstellationEntity originalUserConstellation = createUserConstellation(user, originalName, originalDescription);
        originalUserConstellation.setUserConstellationId(1L);

        UserConstellationRequestDto request = new UserConstellationRequestDto(user.getUserId(), 2L, newName, newConstellationDescription, links);
        List<UserConstellationLinkEntity> savedLinks = createLinks(originalUserConstellation, links);

        // when & then
        when(userConstellationRepository.findByUserAndUserConstellationId(user, 2L)).thenReturn(null);

        assertThatThrownBy(() -> {
            userConstellationService.manageUserConstellation(request, true);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("User Constellation not found for given user and constellationId");
    }

    @Test
    @DisplayName("유저 별자리 전체 조회 성공 테스트")
    void getUserConstellationSuccessTest() {
        // Given
        links.add(createLink(star1.getStarId(), star2.getStarId()));
        links.add(createLink(star2.getStarId(), star3.getStarId()));

        String userConstellationName = "Constellation Test";
        String userConstellationDescription = "Constellation Description";

        UserConstellationEntity userConstellation = createUserConstellation(user, userConstellationName, userConstellationDescription);

        List<UserConstellationEntity> savedUserConstellation = new ArrayList<>();
        savedUserConstellation.add(createUserConstellation(user, userConstellationName, userConstellationDescription));

        List<UserConstellationLinkEntity> savedLinks = createLinks(userConstellation, links);

        when(userConstellationLinkRepository.findByUserConstellation(any(UserConstellationEntity.class))).thenReturn(savedLinks);
        when(userConstellationRepository.findByUser(user)).thenReturn(savedUserConstellation);

        // When
        List<UserConstellationDto> result = userConstellationService.getUserConstellation(user.getUserId());

        // Then
        assertThat(result).isNotNull().hasSize(1)
                .extracting(UserConstellationDto::getName)
                .containsExactlyInAnyOrder(userConstellationName);
    }

    @Test
    @DisplayName("유저 별자리 개별 조회 성공 테스트")
    void getUserConstellationByIdSuccessTest() {
        // Given
        links.add(createLink(star1.getStarId(), star2.getStarId()));
        links.add(createLink(star2.getStarId(), star3.getStarId()));

        String userConstellationName = "Constellation Test";
        String userConstellationDescription = "Constellation Description";

        UserConstellationEntity userConstellation = createUserConstellation(user, userConstellationName, userConstellationDescription);
        userConstellation.setUserConstellationId(1L);

        List<UserConstellationLinkEntity> savedLinks = createLinks(userConstellation, links);

        when(userConstellationLinkRepository.findByUserConstellation(any(UserConstellationEntity.class))).thenReturn(savedLinks);
        when(userConstellationRepository.findByUserAndUserConstellationId(user, 1L)).thenReturn(userConstellation);

        // When
        UserConstellationDto result = userConstellationService.getUserConstellationById(user.getUserId(), 1L);

        // Then
        assertThat(result).isNotNull()
                .extracting(UserConstellationDto::getName)
                .isEqualTo(userConstellationName);
    }

    @Test
    @DisplayName("유저 별자리 링크 조회 성공 테스트")
    void getUserConstellationLinkSuccessTest() {
        // Given
        links.add(createLink(star1.getStarId(), star2.getStarId()));
        links.add(createLink(star2.getStarId(), star3.getStarId()));

        String userConstellationName = "Constellation Test";
        String userConstellationDescription = "Constellation Description";


        UserConstellationEntity userConstellation = createUserConstellation(user, userConstellationName, userConstellationDescription);
        userConstellation.setUserConstellationId(1L);
        List<Long> constellationIds = new ArrayList<>();
        constellationIds.add(1L);

        List<UserConstellationLinkEntity> savedLinks = createLinks(userConstellation, links);
        when(userConstellationRepository.findUserConstellationIdsByUserId(user.getUserId())).thenReturn(constellationIds);
        when(userConstellationLinkRepository.findByUserConstellationId(1L)).thenReturn(savedLinks);

        // When
        Map<String, Object> result = userConstellationService.getUserConstellationLink(user.getUserId());

        // Then
        assertThat(result.get("1")).isNotNull();
    }

    @Test
    @DisplayName("유저 별자리 삭제 성공 테스트")
    void deleteUserConstellationSuccessTest() {
        // Given
        links.add(createLink(star1.getStarId(), star2.getStarId()));
        links.add(createLink(star2.getStarId(), star3.getStarId()));

        String originalName = "Constellation Test";
        String originalDescription = "Constellation Description";

        UserConstellationEntity originalUserConstellation = createUserConstellation(user, originalName, originalDescription);
        originalUserConstellation.setUserConstellationId(1L);

        List<UserConstellationLinkEntity> savedLinks = createLinks(originalUserConstellation, links);

        // When
        when(userConstellationRepository.findByUserAndUserConstellationId(user, 1L)).thenReturn(originalUserConstellation);
        userConstellationService.deleteUserConstellation(user.getUserId(), 1L);

        // Then
        verify(userConstellationLinkRepository).deleteByUserConstellation(originalUserConstellation);
        verify(userConstellationRepository).delete(originalUserConstellation);
    }


    @Test
    @DisplayName("유저 별자리 삭제 실패 테스트 - 사용자 없음")
    void deleteUserConstellationFailTest() {
        links.add(createLink(star1.getStarId(), star2.getStarId()));
        links.add(createLink(star2.getStarId(), star3.getStarId()));

        String originalName = "Constellation Test";
        String originalDescription = "Constellation Description";

        UserConstellationEntity originalUserConstellation = createUserConstellation(user, originalName, originalDescription);
        originalUserConstellation.setUserConstellationId(1L);

        // when & then
        assertThatThrownBy(() -> {
            userConstellationService.deleteUserConstellation("wnck", 1L);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("User not found with id: " + "wnck");
    }

    @Test
    @DisplayName("유저 별자리 삭제 실패 테스트 - 별자리 없음")
    void deleteUserConstellationFailTest2() {
        links.add(createLink(star1.getStarId(), star2.getStarId()));
        links.add(createLink(star2.getStarId(), star3.getStarId()));

        String originalName = "Constellation Test";
        String originalDescription = "Constellation Description";

        UserConstellationEntity originalUserConstellation = createUserConstellation(user, originalName, originalDescription);
        originalUserConstellation.setUserConstellationId(1L);

        List<UserConstellationLinkEntity> savedLinks = createLinks(originalUserConstellation, links);

        // when & then
        when(userConstellationRepository.findByUserAndUserConstellationId(user, 2L)).thenReturn(null);

        assertThatThrownBy(() -> {
            userConstellationService.deleteUserConstellation(user.getUserId(), 2L);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("User Constellation not found for given user and userConstellationId");
    }



    private List<String> createLink(String startStarId, String endStarId) {
        List<String> link = new ArrayList<>();
        link.add(startStarId);
        link.add(endStarId);
        return link;
    }

    private UserConstellationEntity createUserConstellation(UserEntity user, String name, String description) {
        UserConstellationEntity userConstellation = new UserConstellationEntity();
        userConstellation.setUser(user);
        userConstellation.setName(name);
        userConstellation.setDescription(description);
        return userConstellation;
    }

    private List<UserConstellationLinkEntity> createLinks(UserConstellationEntity userConstellation, List<List<String>> links) {
        List<UserConstellationLinkEntity> savedLinks = new ArrayList<>();
        for (List<String> link : links) {
            if (link.get(0).equals(link.get(1))) { continue; }
            UserConstellationLinkEntity userLink = new UserConstellationLinkEntity();
            userLink.setUserConstellation(userConstellation);
            userLink.setStartStar(starRepository.findByStarId(link.get(0)));
            userLink.setEndStar(starRepository.findByStarId(link.get(1)));
            savedLinks.add(userLink);
        }
        return savedLinks;
    }

    private void verifyUserConstellationSaved(UserEntity user, String name) {
        List<UserConstellationEntity> savedUserConstellation = new ArrayList<>();
        savedUserConstellation.add(createUserConstellation(user, name, "Constellation Description"));
        when(userConstellationRepository.findByUser(user)).thenReturn(savedUserConstellation);

        List<UserConstellationEntity> result = userConstellationRepository.findByUser(user);
        assertThat(result).hasSize(1)
                .extracting(UserConstellationEntity::getName)
                .containsExactlyInAnyOrder(name);
    }

    private void verifyLinksSaved(UserConstellationEntity userConstellation, List<UserConstellationLinkEntity> savedLinks) {
        when(userConstellationLinkRepository.findByUserConstellation(userConstellation)).thenReturn(savedLinks);

        List<UserConstellationLinkEntity> resultLinks = userConstellationLinkRepository.findByUserConstellation(userConstellation);
        assertThat(resultLinks).hasSize(2);
    }
}
