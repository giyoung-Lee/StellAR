package com.ssafy.stellar.userConstellation.repository;

import com.ssafy.stellar.star.entity.StarEntity;
import com.ssafy.stellar.user.entity.UserEntity;
import com.ssafy.stellar.userConstellation.entity.UserConstellationEntity;
import com.ssafy.stellar.userConstellation.entity.UserConstellationLinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserConstellationLinkRepository extends JpaRepository<UserConstellationLinkEntity, Long> {
    List<UserConstellationLinkEntity> findByUserConstellation(UserConstellationEntity userConstellation);

    @Transactional
    void deleteByUserConstellation(UserConstellationEntity userConstellation);

}
