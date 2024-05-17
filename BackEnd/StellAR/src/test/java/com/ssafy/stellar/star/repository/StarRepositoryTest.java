package com.ssafy.stellar.star.repository;

import com.ssafy.stellar.star.entity.StarEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("User Star Bookmark Repository Unit-Test")
@ActiveProfiles("h2")
public class StarRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StarRepository starRepository;

    @Test
    @DisplayName("MagV(겉보기 등급) 보다 낮은 별들을 보내주기")
    void testFindAllByMagVLessThan() {
        // Given
        StarEntity star1 = new StarEntity();
        star1.setStarId("star456");
        star1.setMagV("3.0");
        star1.setStarType("normal");
        star1.setConstellation("constellation");
        star1.setHD("현대");
        star1.setRA("");
        star1.setDeclination("");
        star1.setParallax("");
        star1.setPMDEC("");
        star1.setPMRA("");
        star1.setSP_TYPE("");
        entityManager.persist(star1);

        StarEntity star2 = new StarEntity();
        star2.setStarId("star789");
        star2.setMagV("2.0");
        star2.setStarType("star");
        star2.setStarType("normal");
        star2.setConstellation("constellation");
        star2.setHD("현대");
        star2.setRA("");
        star2.setDeclination("");
        star2.setParallax("");
        star2.setPMDEC("");
        star2.setPMRA("");
        star2.setSP_TYPE("");
        entityManager.persist(star2);

        entityManager.flush();

        // When
        List<StarEntity> stars = starRepository.findAllByMagVLessThan("2.5");

        // Then
        assertThat(stars).isNotEmpty();
        assertThat(stars.size()).isEqualTo(1);
        assertThat(stars.get(0).getStarId()).isEqualTo("star789");
    }

    @Test
    @DisplayName("ID로 별찾기")
    void testFindByStarId() {
        // Given
        StarEntity newStar = new StarEntity();
        newStar.setStarId("star123");
        newStar.setMagV("4.5");
        newStar.setStarType("nebula");
        entityManager.persist(newStar);
        entityManager.flush();

        // When
        StarEntity found = starRepository.findByStarId("star123");

        // Then
        assertThat(found).isNotNull();
        assertThat(found.getStarId()).isEqualTo("star123");
    }
}
