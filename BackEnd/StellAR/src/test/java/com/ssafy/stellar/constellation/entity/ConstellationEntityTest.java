package com.ssafy.stellar.constellation.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Constellation Entity Unit-Test")
public class ConstellationEntityTest {

    private ConstellationEntity entity;

    @BeforeEach()
    void setEntity() {
        entity = new ConstellationEntity();

        entity.setConstellationId("constellationId");
        entity.setConstellationSeason("constellationSeason");
        entity.setConstellationAlpha("constellationAlpha");
        entity.setConstellationSubName("constellationSubName");
        entity.setConstellationStartObservation("constellationStartObservation");
        entity.setConstellationImg("constellationImg");
        entity.setConstellationStory("constellationStory");
        entity.setConstellationType("constellationType");
        entity.setConstellationEndObservation("constellationEndObservation");
        entity.setConstellationAlphaDesc("constellationAlphaDesc");

    }

    @Test
    @DisplayName("별자리 엔터티 테스트")
    void getEntity() {
        assertThat(entity.getConstellationId()).isNotNull();
        assertThat(entity.getConstellationSeason()).isNotNull();
        assertThat(entity.getConstellationAlpha()).isNotNull();
        assertThat(entity.getConstellationSubName()).isNotNull();
        assertThat(entity.getConstellationStartObservation()).isNotNull();
        assertThat(entity.getConstellationImg()).isNotNull();
        assertThat(entity.getConstellationStory()).isNotNull();
        assertThat(entity.getConstellationType()).isNotNull();
        assertThat(entity.getConstellationEndObservation()).isNotNull();
        assertThat(entity.getConstellationAlphaDesc()).isNotNull();
    }

}
