package com.ssafy.stellar.constellation.repository;

import com.ssafy.stellar.constellation.entity.ConstellationEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ConstellationEventRepository extends JpaRepository<ConstellationEventEntity, Integer> {
    List<ConstellationEventEntity> findAllByLocdateBetween(LocalDate start, LocalDate end);
}
