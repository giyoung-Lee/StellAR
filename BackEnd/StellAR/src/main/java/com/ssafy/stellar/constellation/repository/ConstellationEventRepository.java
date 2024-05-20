package com.ssafy.stellar.constellation.repository;

import com.ssafy.stellar.constellation.entity.ConstellationEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ConstellationEventRepository extends JpaRepository<ConstellationEventEntity, Integer> {
    List<ConstellationEventEntity> findAllByLocdateBetween(LocalDate start, LocalDate end);

    @Query("SELECT e FROM constellation_event e WHERE " +
            "(e.locdate = :today AND e.astroTime > :time) OR " +
            "(e.locdate = :tomorrow AND e.astroTime < :time)")
    List<ConstellationEventEntity> findEventsByDateAndTime(
            @Param("today") LocalDate today,
            @Param("tomorrow") LocalDate tomorrow,
            @Param("time") String time);
}
