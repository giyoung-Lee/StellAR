package com.ssafy.stellar.star.repository;

import com.ssafy.stellar.star.entity.PlanetEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("User Planet Repository Unit-Test")
public class PlanetRepositoryTest {

    @Autowired
    private PlanetRepository planetRepository;

    @Test
    @DisplayName("모든 행성을 조회")
    void testFindAllPlanets() {
        // Given
        PlanetEntity planet1 = new PlanetEntity();
        planet1.setPlanetId("planet002");
        planet1.setPlanetDEC("123.789");
        planet1.setPlanetMagV("5.6");
        planet1.setPlanetRA("789.456");
        planetRepository.save(planet1);

        PlanetEntity planet2 = new PlanetEntity();
        planet2.setPlanetId("planet003");
        planet2.setPlanetDEC("456.789");
        planet2.setPlanetMagV("6.7");
        planet2.setPlanetRA("123.456");
        planetRepository.save(planet2);


        // When
        List<PlanetEntity> planets = planetRepository.findAll();

        // Then
        assertThat(planets).hasSize(2);
        assertThat(planets).extracting(PlanetEntity::getPlanetId)
                .containsExactlyInAnyOrder("planet002", "planet003");
    }

}
