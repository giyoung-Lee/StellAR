package com.ssafy.stellar.StructureTest.repository;

import com.ssafy.stellar.StructureTest.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findById(Long id);

}
