package com.ssafy.stellar.constellation.repository;


import com.ssafy.stellar.constellation.entity.ConstellationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConstellationRepository extends JpaRepository<ConstellationEntity, String> {


    List<ConstellationEntity> findAllByConstellationType(String constellationType);

    @Query("SELECT c.constellationId FROM constellation c WHERE c.constellationType = :constellationType")
    List<String> findByConstellationType(String constellationType);

}
