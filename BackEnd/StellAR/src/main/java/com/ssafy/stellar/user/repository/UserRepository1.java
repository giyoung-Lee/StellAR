package com.ssafy.stellar.user.repository;

import com.ssafy.stellar.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository1 extends JpaRepository<UserEntity, String> {
    UserEntity findByUserId(String userId);
}
