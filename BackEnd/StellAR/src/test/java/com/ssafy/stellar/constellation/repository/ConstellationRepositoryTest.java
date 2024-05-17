package com.ssafy.stellar.constellation.repository;

import com.ssafy.stellar.constellation.entity.ConstellationEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Constellation Repository Unit-Test")
@ActiveProfiles("h2")
public class ConstellationRepositoryTest {

    @Autowired
    private ConstellationRepository constellationRepository;

    @BeforeEach()
    void setConstellationRepository() {
        ConstellationEntity constellation1 = new ConstellationEntity();

        constellation1.setConstellationId("Aquarius");
        constellation1.setConstellationSeason("가을");
        constellation1.setConstellationAlpha("Sadalsuud");
        constellation1.setConstellationSubName("물병자리");
        constellation1.setConstellationStartObservation("8.17");
        constellation1.setConstellationImg("Aquarius.png");
        constellation1.setConstellationStory("그리스 신화에 의하면 물병자리는 독수리에게 납치당해");
        constellation1.setConstellationType("hwangdo13");
        constellation1.setConstellationEndObservation("9.9");
        constellation1.setConstellationAlphaDesc("겉보기 등급 2.87로, 지구로부터 약 540광년 떨어져있다.");
        constellationRepository.save(constellation1);


        ConstellationEntity constellation2 = new ConstellationEntity();

        constellation2.setConstellationId("bangsu");
        constellation2.setConstellationSeason("여름");
        constellation2.setConstellationAlpha("방수설명");
        constellation2.setConstellationSubName("방수");
        constellation2.setConstellationStartObservation("5월");
        constellation2.setConstellationImg("null");
        constellation2.setConstellationStory("28수(宿)중 네 번째 별자리인");
        constellation2.setConstellationType("3won28su");
        constellation2.setConstellationEndObservation("7월");
        constellation2.setConstellationAlphaDesc("null");
        constellationRepository.save(constellation2);

    }

    @Test
    @DisplayName("findAllByConstellationType - 별자리(황도) 조회")
    void findAllByConstellationType() {
        // Given
        String constellationType = "hwangdo13";

        // When
        List<ConstellationEntity> list =
                constellationRepository.findAllByConstellationType(constellationType);

        // Then
        assertThat(list)
                .hasSize(1)
                .extracting(ConstellationEntity::getConstellationType)
                .doesNotContain("3won28su");
        assertThat(list.get(0).getConstellationId()).isEqualTo("Aquarius");
        assertThat(list.get(0).getConstellationSeason()).isEqualTo("가을");
    }

    @Test
    @DisplayName("findByConstellationType - 별자리 아이디만 조회")
    void findByConstellationType() {
        //Given
        String constellationType = "hwangdo13";

        // When
        List<String> list =
                constellationRepository.findByConstellationType(constellationType);

        // Then
        assertThat(list)
                .hasSize(1)
                .extracting(String::toString)
                .contains("Aquarius");
        assertThat(list.get(0)).isEqualTo("Aquarius");
    }

    @Test
    @DisplayName("findAllByConstellationId - 별자리 아이디로 별자리 조회")
    void findAllByConstellationId() {
        //Given
        String constellationId = "Aquarius";

        // When
        ConstellationEntity entity
                = constellationRepository.findAllByConstellationId(constellationId);

        // Then
        assertThat(entity)
                .extracting(ConstellationEntity::getConstellationId)
                .isEqualTo("Aquarius");

        assertThat(entity.getConstellationType()).isEqualTo("hwangdo13");
    }


}
