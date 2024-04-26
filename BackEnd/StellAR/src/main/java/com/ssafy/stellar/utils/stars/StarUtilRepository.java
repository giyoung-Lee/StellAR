package com.ssafy.stellar.utils.stars;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ssafy.stellar.star.entity.StarEntity;

@Repository
public interface StarUtilRepository extends JpaRepository<StarEntity, String> {
}
