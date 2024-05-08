package com.ssafy.stellar.userConstellation.repository;

import com.ssafy.stellar.user.entity.UserEntity;
import com.ssafy.stellar.userConstellation.entity.UserConstellationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserConstellationRepository extends JpaRepository<UserConstellationEntity, Long> {
    List<UserConstellationEntity> findByUser(UserEntity user);

    UserConstellationEntity findByUserAndUserConstellationId(UserEntity user, Long userConstellationId);

    @Query("SELECT uce.userConstellationId FROM user_constellation uce WHERE uce.user.userId = :userId")
    List<Long> findUserConstellationIdsByUserId(String userId);

    @Transactional
    void deleteByUserAndUserConstellationId(UserEntity user, Long userConstellationId);

}
