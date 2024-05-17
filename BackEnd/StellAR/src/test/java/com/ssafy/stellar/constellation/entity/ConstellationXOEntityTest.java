package com.ssafy.stellar.constellation.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Constellation XO Entity Unit-test")
public class ConstellationXOEntityTest {

    private ConstellationXOEntity entity;

    @BeforeEach()
    void setEntity() {
        entity = new ConstellationXOEntity();

        entity.setConstellationQuestionId(1);
        entity.setConstellationQuestionContents("contents");
        entity.setConstellationQuestionAnswer("answer");

        ConstellationEntity entity1 = new ConstellationEntity();
        entity1.setConstellationId("constellationId");
        entity.setConstellationId(entity1);
    }

    @Test
    @DisplayName("별자리 퀴즈 엔터티 테스트")
    void getEntity() {
        assertThat(entity.getConstellationQuestionId()).isNotNull();
        assertThat(entity.getConstellationQuestionContents()).isNotNull();
        assertThat(entity.getConstellationQuestionAnswer()).isNotNull();
        assertThat(entity.getConstellationId()).isNotNull();
    }

}
