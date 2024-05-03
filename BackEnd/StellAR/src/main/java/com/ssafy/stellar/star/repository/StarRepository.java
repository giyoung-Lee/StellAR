package com.ssafy.stellar.star.repository;

import com.ssafy.stellar.star.entity.StarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StarRepository extends JpaRepository<StarEntity, String> {

    @Query("SELECT s FROM star s WHERE s.MagV < :maxMagv")
    List<StarEntity> findAllByMagVLessThan(@Param("maxMagv") String maxMagv);
    StarEntity findByStarId(String starId);
}
