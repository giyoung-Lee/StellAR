package com.ssafy.stellar.constellation.repository;

import com.ssafy.stellar.constellation.entity.ConstellationLinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConstellationLinkRepository extends JpaRepository<ConstellationLinkEntity, Integer> {

    @Query("SELECT c FROM constellation_link c WHERE c.constellationId = :constellationId")
    List<ConstellationLinkEntity> findAllByConstellationId(String constellationId);

}
