package com.ssafy.stellar.star.entity;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Star Entity Unit-test")
public class StarEntityTest {

    @Test
    @DisplayName("별 엔터티 테스트")
    void setEntity (){
        StarEntity entity = new StarEntity();

        entity.setStarId("starId");
        assertThat(entity.getStarId()).isNotNull();

        entity.setRA("starRa");
        assertThat(entity.getRA()).isNotNull();

        entity.setDeclination("starDeclination");
        assertThat(entity.getDeclination()).isNotNull();

        entity.setPMRA("starPMRA");
        assertThat(entity.getPMRA()).isNotNull();

        entity.setPMDEC("starPMDEC");
        assertThat(entity.getPMDEC()).isNotNull();

        entity.setMagV("starMagV");
        assertThat(entity.getMagV()).isNotNull();

        entity.setParallax("starParallax");
        assertThat(entity.getParallax()).isNotNull();

        entity.setSP_TYPE("starSPType");
        assertThat(entity.getSP_TYPE()).isNotNull();

        entity.setConstellation("starConstellation");
        assertThat(entity.getConstellation()).isNotNull();

        entity.setHD("starHD");
        assertThat(entity.getHD()).isNotNull();

        entity.setStarType("starStarType");
        assertThat(entity.getStarType()).isNotNull();
    }


}
