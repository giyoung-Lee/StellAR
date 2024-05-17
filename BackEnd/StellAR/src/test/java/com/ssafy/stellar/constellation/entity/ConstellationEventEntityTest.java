package com.ssafy.stellar.constellation.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Constellation Event Unit-Test")
public class ConstellationEventEntityTest {

    private ConstellationEventEntity entity;

    @BeforeEach()
    void setEntity() {
        entity = new ConstellationEventEntity();

        entity.setConstellationEventId(1);
        entity.setAstroEvent("AstroEvent");
        entity.setAstroTime("AstroTime");
        LocalDate today = LocalDate.now();
        entity.setLocdate(today);
    }

    @Test
    @DisplayName("별자리 이벤트 엔터티 테스트")
    void getEntity() {
        assertThat(entity.getConstellationEventId()).isNotNull();
        assertThat(entity.getAstroEvent()).isNotNull();
        assertThat(entity.getAstroTime()).isNotNull();
        assertThat(entity.getLocdate()).isNotNull();
    }

}
