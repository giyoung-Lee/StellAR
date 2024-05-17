package com.ssafy.stellar.constellation.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ssafy.stellar.constellation.dto.response.ConstellationDto;
import com.ssafy.stellar.constellation.dto.response.ConstellationEventDto;
import com.ssafy.stellar.constellation.dto.response.ConstellationXODto;
import com.ssafy.stellar.constellation.entity.ConstellationEntity;
import com.ssafy.stellar.constellation.entity.ConstellationEventEntity;
import com.ssafy.stellar.constellation.entity.ConstellationLinkEntity;
import com.ssafy.stellar.constellation.entity.ConstellationXOEntity;
import com.ssafy.stellar.constellation.repository.ConstellationEventRepository;
import com.ssafy.stellar.constellation.repository.ConstellationLinkRepository;
import com.ssafy.stellar.constellation.repository.ConstellationRepository;
import com.ssafy.stellar.constellation.repository.ConstellationXORepository;
import com.ssafy.stellar.star.entity.StarEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConstellationServiceImplTest {

    @Mock
    private ConstellationRepository constellationRepository;

    @Mock
    private ConstellationLinkRepository constellationLinkRepository;

    @Mock
    private ConstellationEventRepository constellationEventRepository;

    @Mock
    private ConstellationXORepository constellationXORepository;

    @InjectMocks
    private ConstellationServiceImpl constellationService;

    private StarEntity starA;
    private StarEntity starB;

    @BeforeEach
    void setUp() {
        Gson gson = new Gson();

        starA = new StarEntity();
        starA.setStarId("1 Aquarius");
        starA.setMagV("5.153");
        starA.setStarType("normal");
        starA.setConstellation("1 Aquarius");
        starA.setHD("HD 196758");
        starA.setRA("20 39 24.8927");
        starA.setDeclination("+00 29 11.155");
        starA.setParallax("12.3852");
        starA.setPMDEC("-10.14");
        starA.setPMRA("96.805");
        starA.setSP_TYPE("K");


        starB = new StarEntity();
        starB.setStarId("1 Aries");
        starB.setMagV("5.86");
        starB.setStarType("star");
        starB.setStarType("normal");
        starB.setConstellation("1 Aries");
        starB.setHD("현대");
        starB.setRA("01 50 08.5698");
        starB.setDeclination("+22 16 31.210");
        starB.setParallax("5.57");
        starB.setPMDEC("-8.25");
        starB.setPMRA("-16.52");
        starB.setSP_TYPE("G");
    }

    @Test
    void testFindAllConstellation() {
        String constellationType = "type";
        ConstellationEntity entity = new ConstellationEntity();
        entity.setConstellationId("id");
        entity.setConstellationType(constellationType);
        entity.setConstellationSeason("season");
        entity.setConstellationAlpha("alpha");
        entity.setConstellationSubName("subName");
        entity.setConstellationStartObservation("start");
        entity.setConstellationEndObservation("end");
        entity.setConstellationImg("image.png");
        entity.setConstellationStory("story");

        when(constellationRepository.findAllByConstellationType(constellationType)).thenReturn(List.of(entity));

        // Mocking ServletUriComponentsBuilder
        try (MockedStatic<ServletUriComponentsBuilder> mockedBuilder = mockStatic(ServletUriComponentsBuilder.class)) {
            ServletUriComponentsBuilder builder = mock(ServletUriComponentsBuilder.class);
            mockedBuilder.when(ServletUriComponentsBuilder::fromCurrentContextPath).thenReturn(builder);
            when(builder.path(anyString())).thenReturn(builder);
            when(builder.toUriString()).thenReturn("mockedUri");

            List<ConstellationDto> result = constellationService.findAllConstellation(constellationType);

            assertEquals(1, result.size());
            ConstellationDto dto = result.get(0);
            assertEquals("id", dto.getConstellationId());
            assertEquals("mockedUri", dto.getConstellationImg());
        }

        verify(constellationRepository, times(1)).findAllByConstellationType(constellationType);
    }

    @Test
    void testFindConstellationLink() {
        String constellationType = "type";
        String constellationId = "id";
        ConstellationLinkEntity linkEntity = new ConstellationLinkEntity();
        linkEntity.setStarA(starA);
        linkEntity.setStarB(starB);

        when(constellationRepository.findByConstellationType(constellationType)).thenReturn(List.of(constellationId));
        when(constellationLinkRepository.findAllByConstellationId(constellationId)).thenReturn(List.of(linkEntity));

        Map<String, Object> result = constellationService.findConstellationLink(constellationType);

        assertNotNull(result);
        verify(constellationRepository, times(1)).findByConstellationType(constellationType);
        verify(constellationLinkRepository, times(1)).findAllByConstellationId(constellationId);
    }

    @Test
    void testFindConstellationById() {
        String constellationId = "id";
        ConstellationEntity entity = new ConstellationEntity();
        entity.setConstellationId(constellationId);
        entity.setConstellationSeason("season");
        entity.setConstellationAlpha("alpha");
        entity.setConstellationSubName("subName");
        entity.setConstellationStartObservation("start");
        entity.setConstellationEndObservation("end");
        entity.setConstellationImg("image.png");
        entity.setConstellationStory("story");

        when(constellationRepository.findAllByConstellationId(constellationId)).thenReturn(entity);

        // Mocking ServletUriComponentsBuilder
        try (MockedStatic<ServletUriComponentsBuilder> mockedBuilder = mockStatic(ServletUriComponentsBuilder.class)) {
            ServletUriComponentsBuilder builder = mock(ServletUriComponentsBuilder.class);
            mockedBuilder.when(ServletUriComponentsBuilder::fromCurrentContextPath).thenReturn(builder);
            when(builder.path(anyString())).thenReturn(builder);
            when(builder.toUriString()).thenReturn("mockedUri");

            ConstellationDto result = constellationService.findConstellationById(constellationId);

            assertEquals(constellationId, result.getConstellationId());
            assertEquals("mockedUri", result.getConstellationImg());
            verify(constellationRepository, times(1)).findAllByConstellationId(constellationId);
        }
    }

    @Test
    void testReturnConstellationEvent() {
        ConstellationEventEntity entity = new ConstellationEventEntity();
        entity.setAstroEvent("event");
        entity.setAstroTime("time");
        entity.setLocdate(LocalDate.now());

        when(constellationEventRepository.findAllByLocdateBetween(any(), any())).thenReturn(List.of(entity));

        List<ConstellationEventDto> result = constellationService.returnConstellationEvent();

        assertEquals(1, result.size());
        ConstellationEventDto dto = result.get(0);
        assertEquals("event", dto.getAstroEvent());
        verify(constellationEventRepository, times(1)).findAllByLocdateBetween(any(), any());
    }

    @Test
    void testReturnConstellationXO() {
        String constellationId = "id";
        ConstellationXOEntity entity = new ConstellationXOEntity();
        ConstellationEntity constellationEntity = new ConstellationEntity();
        constellationEntity.setConstellationId(constellationId);
        entity.setConstellationId(constellationEntity);
        entity.setConstellationQuestionContents("contents");
        entity.setConstellationQuestionAnswer("answer");

        when(constellationXORepository.findAllByConstellationId(constellationId)).thenReturn(List.of(entity));

        List<ConstellationXODto> result = constellationService.returnConstellationXO(constellationId);

        assertEquals(1, result.size());
        ConstellationXODto dto = result.get(0);
        assertEquals(constellationId, dto.getConstellationId());
        verify(constellationXORepository, times(1)).findAllByConstellationId(constellationId);
    }
}
