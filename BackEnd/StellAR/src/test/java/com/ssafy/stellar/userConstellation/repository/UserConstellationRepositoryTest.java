package com.ssafy.stellar.userConstellation.repository;

import com.ssafy.stellar.user.entity.UserEntity;
import com.ssafy.stellar.user.repository.UserRepository;
import com.ssafy.stellar.userConstellation.entity.UserConstellationEntity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DisplayName("User Constellation Repository Unit-Test")
@ActiveProfiles("h2")
public class UserConstellationRepositoryTest {

    @Autowired
    UserConstellationRepository userConstellationRepository;
    @Autowired
    private UserRepository userRepository;

    private UserEntity user;

    @BeforeEach
    void setup() {
        user = new UserEntity();
        user.setUserId("wncks");
        userRepository.save(user);
    }

    @Test
    @DisplayName("유저 별자리 리스트 조회 단위 테스트")
    void findByUserTest() {
        // Given
        String constellationName1 = "test1";
        String constellationName2 = "test2";
        String constellationDiscription1 = "discription1";
        String constellationDiscription2 = "discription2";

        UserConstellationEntity userConstellation1 = new UserConstellationEntity();
        userConstellation1.setUser(user);
        userConstellation1.setName(constellationName1);
        userConstellation1.setDescription(constellationDiscription1);
        userConstellationRepository.save(userConstellation1);

        UserConstellationEntity userConstellation2 = new UserConstellationEntity();
        userConstellation2.setUser(user);
        userConstellation2.setName(constellationName2);
        userConstellation2.setDescription(constellationDiscription2);
        userConstellationRepository.save(userConstellation2);

        // When
        List<UserConstellationEntity> result = userConstellationRepository.findByUserOrderByCreateDateTimeDesc(user);
        System.out.println("result.get(0) = " + result.get(0).getName());
        System.out.println("result.get(1) = " + result.get(1).getName());

        // Then
        assertThat(result).hasSize(2).extracting(UserConstellationEntity::getName)
                .containsExactlyInAnyOrder(constellationName1, constellationName2);

    }

    @Test
    @DisplayName("유저 별자리 개별 조회 단위 테스트")
    void findByUserAndUserConstellationIdTest() {
        // Given
        String constellationName1 = "test1";
        String constellationDiscription1 = "discription1";

        UserConstellationEntity userConstellation1 = new UserConstellationEntity();
        userConstellation1.setUser(user);
        userConstellation1.setName(constellationName1);
        userConstellation1.setDescription(constellationDiscription1);
        userConstellationRepository.save(userConstellation1);

        // When
        UserConstellationEntity result = userConstellationRepository.findByUserAndUserConstellationId(user, 1L);

        // Then
        assertThat(result).isNotNull()
                .extracting(UserConstellationEntity::getName)
                .isEqualTo(constellationName1);
    }

    @Test
    @DisplayName("유저 별자리 ID 리스트 조회 테스트")
    void findUserConstellationIdsByUserIdTest() {
        // Given
        String constellationName1 = "test1";
        String constellationName2 = "test2";
        String constellationDiscription1 = "discription1";
        String constellationDiscription2 = "discription2";

        UserConstellationEntity userConstellation1 = new UserConstellationEntity();
        userConstellation1.setUser(user);
        userConstellation1.setName(constellationName1);
        userConstellation1.setDescription(constellationDiscription1);
        userConstellationRepository.save(userConstellation1);

        UserConstellationEntity userConstellation2 = new UserConstellationEntity();
        userConstellation2.setUser(user);
        userConstellation2.setName(constellationName2);
        userConstellation2.setDescription(constellationDiscription2);
        userConstellationRepository.save(userConstellation2);
        // When
        List<Long> result = userConstellationRepository.findUserConstellationIdsByUserId("wncks");
        System.out.println("result.get(0) = " + result.get(0));
        System.out.println("result.get(0) = " + result.get(1));

        // Then
        assertThat(result).hasSize(2);
    }
}
