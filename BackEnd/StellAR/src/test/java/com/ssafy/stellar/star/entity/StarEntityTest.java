package com.ssafy.stellar.star.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Star Entity Unit-test")
public class StarEntityTest {

    private StarEntity entity;

    @BeforeEach()
    void setStar() {
        entity = new StarEntity();

        entity.setStarId("starId");
        entity.setRA("starRa");
        entity.setDeclination("starDeclination");
        entity.setPMRA("starPMRA");
        entity.setPMDEC("starPMDEC");
        entity.setMagV("starMagV");
        entity.setParallax("starParallax");
        entity.setSP_TYPE("starSPType");
        entity.setConstellation("starConstellation");
        entity.setHD("starHD");
        entity.setStarType("starStarType");

    }

    @Test
    @DisplayName("별 엔터티 테스트")
    void getEntity (){
        assertThat(entity.getStarId()).isNotNull();
        assertThat(entity.getRA()).isNotNull();
        assertThat(entity.getDeclination()).isNotNull();
        assertThat(entity.getPMRA()).isNotNull();
        assertThat(entity.getPMDEC()).isNotNull();
        assertThat(entity.getMagV()).isNotNull();
        assertThat(entity.getParallax()).isNotNull();
        assertThat(entity.getSP_TYPE()).isNotNull();
        assertThat(entity.getConstellation()).isNotNull();
        assertThat(entity.getHD()).isNotNull();
        assertThat(entity.getStarType()).isNotNull();
    }
}
