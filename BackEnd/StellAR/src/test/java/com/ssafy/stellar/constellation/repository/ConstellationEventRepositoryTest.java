package com.ssafy.stellar.constellation.repository;

import com.ssafy.stellar.constellation.entity.ConstellationEventEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Constellation Event Repository Unit-Test")
@ActiveProfiles("h2")
public class ConstellationEventRepositoryTest {

    @Autowired
    private ConstellationEventRepository constellationEventRepository;

    @BeforeEach
    void setConstellationEventRepository() {
        ConstellationEventEntity entity1 = new ConstellationEventEntity();
        entity1.setConstellationEventId(1);
        entity1.setAstroEvent("AstroEvent1");
        entity1.setAstroTime("01:00");
        entity1.setLocdate(LocalDate.now());
        constellationEventRepository.save(entity1);


        ConstellationEventEntity entity2 = new ConstellationEventEntity();
        entity2.setConstellationEventId(2);
        entity2.setAstroEvent("AstroEvent2");
        entity2.setAstroTime("01:00");
        entity2.setLocdate(LocalDate.now().plusDays(1));
        constellationEventRepository.save(entity2);
    }

    @Test
    @DisplayName("findAllByLocdateBetween - 기간내의 이벤트")
    void findAllByLocdateBetween() {
        // Given
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(5);

        // When
        List<ConstellationEventEntity> list
                = constellationEventRepository.findAllByLocdateBetween(startDate, endDate);

        // Then
        assertThat(list)
                .hasSize(2)
                .extracting(ConstellationEventEntity::getConstellationEventId)
                .contains(1);

        assertThat(list.get(0).getConstellationEventId()).isEqualTo(1);
    }

    @Test
    @DisplayName("findEventsByDateAndTime - 시간 내의 이벤트")
    void findEventsByDateAndTime() {
        // Given
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(1);
        String cutoffTime = "01:05";

        // When
        List<ConstellationEventEntity> list =
                constellationEventRepository.findEventsByDateAndTime(
                        startDate, endDate, cutoffTime
                );

        // Then
        assertThat(list)
                .hasSize(1)
                .extracting(ConstellationEventEntity::getAstroEvent)
                .contains("AstroEvent2");

        assertThat(list.get(0).getAstroEvent()).isEqualTo("AstroEvent2");
    }


}
