package com.ssafy.stellar.star.repository;

import com.ssafy.stellar.star.entity.PlanetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanetRepository extends JpaRepository<PlanetEntity, String> {

}
