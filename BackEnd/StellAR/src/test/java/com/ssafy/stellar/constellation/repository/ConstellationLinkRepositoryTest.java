package com.ssafy.stellar.constellation.repository;

import com.ssafy.stellar.constellation.entity.ConstellationLinkEntity;
import com.ssafy.stellar.star.entity.StarEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Constellation link Repository Unit-Test")
public class ConstellationLinkRepositoryTest {

    @Autowired
    private ConstellationLinkRepository constellationLinkRepository;

    @Autowired
    private TestEntityManager entityManager;

    private StarEntity star1;
    private StarEntity star2;
    private ConstellationLinkEntity link1;

    @BeforeEach
    void setUp() {
        star1 = new StarEntity();
        star1.setStarId("star1");
        star1 = entityManager.persistAndFlush(star1);

        star2 = new StarEntity();
        star2.setStarId("star2");
        star2 = entityManager.persistAndFlush(star2);

        link1 = new ConstellationLinkEntity();
        link1.setConstellationId("Aquarius");
        link1.setStarA(star1);
        link1.setStarB(star2);
        constellationLinkRepository.save(link1);

        ConstellationLinkEntity link2 = new ConstellationLinkEntity();
        link2.setConstellationId("Pisces");
        link2.setStarA(star1);
        link2.setStarB(star2);
        constellationLinkRepository.save(link2);
    }

    @Test
    @DisplayName("findAllByConstellationId - 아이디로 별자리 찾기")
    void findAllByConstellationId() {
        // Given
        String constellationId = "Aquarius";

        // When
        List<ConstellationLinkEntity> list =
                constellationLinkRepository.findAllByConstellationId(constellationId);

        // Then
        assertThat(list)
                .hasSize(1)
                .extracting(ConstellationLinkEntity::getConstellationId)
                .containsExactly("Aquarius");

        assertThat(list.get(0).getStarA().getStarId()).isEqualTo("star1");
        assertThat(list.get(0).getStarB().getStarId()).isEqualTo("star2");
    }
}