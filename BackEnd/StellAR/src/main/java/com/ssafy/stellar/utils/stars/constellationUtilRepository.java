package com.ssafy.stellar.utils.stars;

import com.ssafy.stellar.constellation.entity.ConstellationLinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface constellationUtilRepository extends JpaRepository<ConstellationLinkEntity, Integer> {
}
