package com.ssafy.stellar.userConstellation.repository;

import com.ssafy.stellar.star.entity.StarEntity;
import com.ssafy.stellar.user.entity.UserEntity;
import com.ssafy.stellar.userConstellation.entity.UserConstellationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserConstellationRepository extends JpaRepository<UserConstellationEntity, Long> {
    List<UserConstellationEntity> findByUser(UserEntity user);

    UserConstellationEntity findByUserAndUserConstellationId(UserEntity user, Long userConstellationId);

    UserConstellationEntity findByUserAndName(UserEntity user, String name);


    @Transactional
    void deleteByUserAndName(UserEntity user, String name);

}
