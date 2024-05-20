package com.ssafy.stellar.star.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Planet Entity Unit-test")
public class PlanetEntityTest {

    private PlanetEntity entity;

    @BeforeEach
    void setPlanet() {
        entity = new PlanetEntity();

        entity.setPlanetId("planetId");
        entity.setPlanetRA("RA");
        entity.setPlanetDEC("DEC");
        entity.setPlanetMagV("MagV");

    }

    @Test
    @DisplayName("행성 엔터티 테스트")
    void getEntity () {
        assertThat(entity.getPlanetId()).isNotNull();
        assertThat(entity.getPlanetRA()).isNotNull();
        assertThat(entity.getPlanetMagV()).isNotNull();
        assertThat(entity.getPlanetDEC()).isNotNull();
    }

}
