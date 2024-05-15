package com.ssafy.stellar.star.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import com.ssafy.stellar.star.dto.response.PlanetDto;
import com.ssafy.stellar.star.entity.PlanetEntity;
import com.ssafy.stellar.star.entity.StarEntity;
import com.ssafy.stellar.star.repository.PlanetRepository;
import com.ssafy.stellar.star.repository.StarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
@DisplayName("Star Service Unit-Test")
public class StarServiceImplTest {

    @Mock
    private StarRepository starRepository;

    @Mock
    private PlanetRepository planetRepository;

    @InjectMocks
    private StarServiceImpl starService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(starService, "starMaxMagv", 8.25);
        ReflectionTestUtils.setField(starService, "starMinMagv", -1.46);
    }

    @Test
    @DisplayName("모든 별 정보 반환 테스트")
    void returnAllStar() {
        // Given
        StarEntity star1 = new StarEntity();
        star1.setStarId("1 Aquarius");
        star1.setMagV("5.153");
        star1.setStarType("normal");
        star1.setConstellation("1 Aquarius");
        star1.setHD("HD 196758");
        star1.setRA("20 39 24.8927");
        star1.setDeclination("+00 29 11.155");
        star1.setParallax("12.3852");
        star1.setPMDEC("-10.14");
        star1.setPMRA("96.805");
        star1.setSP_TYPE("K");

        StarEntity star2 = new StarEntity();
        star2.setStarId("1 Aries");
        star2.setMagV("5.86");
        star2.setStarType("star");
        star2.setStarType("normal");
        star2.setConstellation("1 Aries");
        star2.setHD("현대");
        star2.setRA("01 50 08.5698");
        star2.setDeclination("+22 16 31.210");
        star2.setParallax("5.57");
        star2.setPMDEC("-8.25");
        star2.setPMRA("-16.52");
        star2.setSP_TYPE("G");

        List<StarEntity> stars = Arrays.asList(star1, star2);
        when(starRepository.findAllByMagVLessThan("6")).thenReturn(stars);

        // When
        Map<String, Object> result = starService.returnAllStar("6");

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
    }

    @Test
    @DisplayName("모든 행성 정보 반환 테스트")
    void returnPlanet() {
        // Given
        PlanetEntity planet = new PlanetEntity();
        planet.setPlanetId("Jupiter");
        planet.setPlanetMagV("-1.995");
        planet.setPlanetRA("03 38 19.15");
        planet.setPlanetDEC("+18 41 50.2");
        List<PlanetEntity> planets = Arrays.asList(planet);
        when(planetRepository.findAll()).thenReturn(planets);

        // When
        List<PlanetDto> result = starService.returnPlanet();

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getPlanetId()).isEqualTo("Jupiter");
    }

    @Test
    @DisplayName("calculateNewRA - 잘못된 RA format")
    void testCalculateNewRA_InvalidFormat() {
        // Given
        String invalidRA = "20 39"; // Missing seconds part
        double pmRA = 96.805;
        long years = 20;

        // When
        double result = starService.calculateNewRA(invalidRA, pmRA, years);

        // Then
        assertEquals(0, result, "calculateNewRA should return 0 for invalid RA format");
    }

    @Test
    @DisplayName("calculateNewDec - 잘못된 Dec format")
    void testCalculateNewDec_InvalidFormat() {
        // Given
        String invalidDec = "-00 29"; // Missing seconds part
        double pmDec = -10.14;
        long years = 20;

        // When
        double result = starService.calculateNewDec(invalidDec, pmDec, years);

        // Then
        assertEquals(0, result, "calculateNewDec should return 0 for invalid Dec format");
    }

    @Test
    @DisplayName("calculateNewRA - NumberFormatException 에러")
    void testCalculateNewRA_NonNumeric() {
        // Given
        String nonNumericRA = "20 XX 24.8927"; // Non-numeric minute part
        double pmRA = 96.805;
        long years = 20;

        // When
        double result = starService.calculateNewRA(nonNumericRA, pmRA, years);

        // Then
        assertEquals(0, result, "calculateNewRA should return 0 when RA contains non-numeric values");
    }

    @Test
    @DisplayName("calculateNewDec - NumberFormatException 에러")
    void testCalculateNewDec_NonNumeric() {
        // Given
        String nonNumericDec = "+00 29 XX"; // Non-numeric seconds part
        double pmDec = -10.14;
        long years = 20;

        // When
        double result = starService.calculateNewDec(nonNumericDec, pmDec, years);

        // Then
        assertEquals(0, result, "calculateNewDec should return 0 when Dec contains non-numeric values");
    }
}
