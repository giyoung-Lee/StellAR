package com.ssafy.stellar.constellation.entity;

import com.ssafy.stellar.star.entity.StarEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Constellation Link Entity Unit-test")
public class ConstellationLinkEntityTest {

    private ConstellationLinkEntity entity;

    @BeforeEach()
    void setEntity() {
        entity = new ConstellationLinkEntity();

        entity.setLinkId(1);

        StarEntity star1 = new StarEntity();
        star1.setStarId("star1");
        entity.setStarA(star1);

        StarEntity star2 = new StarEntity();
        star2.setStarId("star2");
        entity.setStarB(star2);

        entity.setConstellationId("Scorpios");
    }

    @Test
    @DisplayName("별자리 연결선 테스트")
    void getEntity() {
        assertThat(entity.getLinkId()).isNotNull();
        assertThat(entity.getStarA()).isNotNull();
        assertThat(entity.getStarB()).isNotNull();
        assertThat(entity.getConstellationId()).isNotNull();
    }
}
