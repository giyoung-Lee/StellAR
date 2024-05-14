package com.ssafy.stellar.userConstellation.repository;

import com.ssafy.stellar.star.entity.StarEntity;
import com.ssafy.stellar.star.repository.StarRepository;
import com.ssafy.stellar.user.entity.UserEntity;
import com.ssafy.stellar.user.repository.UserRepository;
import com.ssafy.stellar.userConstellation.entity.UserConstellationEntity;
import com.ssafy.stellar.userConstellation.entity.UserConstellationLinkEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@DataJpaTest
@DisplayName("User Constellation Link Repository Unit-Test")
public class UserConstellationLinkRepositoryTest {

    @Autowired
    private UserConstellationLinkRepository userConstellationLinkRepository;
    @Autowired
    private UserConstellationRepository userConstellationRepository ;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StarRepository starRepository;

    private UserEntity user;
    private StarEntity star1, star2;
    private UserConstellationEntity userConstellation;

    @BeforeEach
    void setUp() {
        user = new UserEntity();
        user.setUserId("wncks");
        userRepository.save(user);

        star1 = new StarEntity();
        star1.setStarId("star1");
        starRepository.save(star1);

        star2 = new StarEntity();
        star2.setStarId("star2");
        starRepository.save(star2);

        userConstellation = new UserConstellationEntity();
        userConstellation.setUser(user);
        userConstellation.setName("주찬자리");
        userConstellation.setDescription("주찬자리 설명");
        userConstellationRepository.save(userConstellation);
    }

    @Test
    @DisplayName("유저 별자리 링크 리스트 반환 테스트")
    void findByUserConstellationTest() {
        // Given
        UserConstellationLinkEntity userConstellationLink = new UserConstellationLinkEntity();
        userConstellationLink.setUserConstellation(userConstellation);
        userConstellationLink.setStartStar(star1);
        userConstellationLink.setEndStar(star2);
        userConstellationLinkRepository.save(userConstellationLink);

        // When
        List<UserConstellationLinkEntity> result = userConstellationLinkRepository.findByUserConstellation(userConstellation);

        // Then
        assertThat(result).hasSize(1).extracting(UserConstellationLinkEntity::getStartStar)
                .containsExactlyInAnyOrder(star1);
    }

    @Test
    @DisplayName("유저 별자리 링크 삭제 테스트")
    void deleteByUserConstellation() {
        // Given
        UserConstellationLinkEntity userConstellationLink = new UserConstellationLinkEntity();
        userConstellationLink.setUserConstellation(userConstellation);
        userConstellationLink.setStartStar(star1);
        userConstellationLink.setEndStar(star2);
        userConstellationLinkRepository.save(userConstellationLink);

        // When
        userConstellationLinkRepository.deleteByUserConstellation(userConstellation);

        // Then
        assertThat(userConstellationLinkRepository.findByUserConstellation(userConstellation));

    }
}
