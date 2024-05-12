package com.ssafy.stellar.constellation.repository;

import com.ssafy.stellar.constellation.entity.ConstellationXOEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConstellationXORepository extends JpaRepository<ConstellationXOEntity, Integer> {

    @Query("SELECT c FROM constellation_question c WHERE c.constellationId.constellationId = :constellationId")
    List<ConstellationXOEntity> findAllByConstellationId(String constellationId);
}
