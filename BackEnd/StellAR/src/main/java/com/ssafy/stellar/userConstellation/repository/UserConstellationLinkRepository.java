package com.ssafy.stellar.userConstellation.repository;

import com.ssafy.stellar.userConstellation.entity.UserConstellationEntity;
import com.ssafy.stellar.userConstellation.entity.UserConstellationLinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserConstellationLinkRepository extends JpaRepository<UserConstellationLinkEntity, Long> {
    List<UserConstellationLinkEntity> findByUserConstellation(UserConstellationEntity userConstellation);

    @Transactional
    void deleteByUserConstellation(UserConstellationEntity userConstellation);

    @Query("SELECT ucl FROM user_constellation_link ucl " +
            "WHERE ucl.userConstellation.userConstellationId = :userConstellationId")
    List<UserConstellationLinkEntity> findByUserConstellationId(Long userConstellationId);

}
