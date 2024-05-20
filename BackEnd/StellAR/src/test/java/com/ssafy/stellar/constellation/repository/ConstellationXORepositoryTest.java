package com.ssafy.stellar.constellation.repository;

import com.ssafy.stellar.constellation.entity.ConstellationEntity;
import com.ssafy.stellar.constellation.entity.ConstellationXOEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Constellation XO Repository Unit-Test")
@ActiveProfiles("h2")
public class ConstellationXORepositoryTest {

    @Autowired
    private ConstellationXORepository constellationXORepository;

    @Autowired
    private TestEntityManager entityManager;

    private ConstellationEntity constellation;

    @BeforeEach
    void setUp() {
        // 별자리 엔티티 생성 및 저장
        constellation = new ConstellationEntity();
        constellation.setConstellationId("Aquarius");
        constellation.setConstellationSeason("가을");
        constellation.setConstellationAlpha("Sadalsuud");
        constellation.setConstellationSubName("물병자리");
        constellation.setConstellationStartObservation("8.17");
        constellation.setConstellationImg("Aquarius.png");
        constellation.setConstellationStory("그리스 신화에 의하면 물병자리는 독수리에게 납치당해");
        constellation.setConstellationType("hwangdo13");
        constellation.setConstellationEndObservation("9.9");
        constellation.setConstellationAlphaDesc("겉보기 등급 2.87로, 지구로부터 약 540광년 떨어져있다.");
        constellation = entityManager.persistAndFlush(constellation);

        // ConstellationXO 엔티티 생성 및 저장
        ConstellationXOEntity question1 = new ConstellationXOEntity();
        question1.setConstellationQuestionId(1);
        question1.setConstellationQuestionContents("This is question 1?");
        question1.setConstellationQuestionAnswer("Yes");
        question1.setConstellationId(constellation);
        constellationXORepository.save(question1);

        ConstellationXOEntity question2 = new ConstellationXOEntity();
        question2.setConstellationQuestionId(2);
        question2.setConstellationQuestionContents("This is question 2?");
        question2.setConstellationQuestionAnswer("No");
        question2.setConstellationId(constellation);
        constellationXORepository.save(question2);
    }

    @Test
    @DisplayName("findAllByConstellationId - 아이디로 XO 질문 찾기")
    void findAllByConstellationId() {
        // Given
        String constellationId = "Aquarius";

        // When
        List<ConstellationXOEntity> list =
                constellationXORepository.findAllByConstellationId(constellationId);

        // Then
        assertThat(list)
                .hasSize(2)
                .extracting(ConstellationXOEntity::getConstellationQuestionContents)
                .containsExactlyInAnyOrder("This is question 1?", "This is question 2?");

        assertThat(list.get(0).getConstellationId().getConstellationId()).isEqualTo("Aquarius");
        assertThat(list.get(1).getConstellationId().getConstellationId()).isEqualTo("Aquarius");
    }
}