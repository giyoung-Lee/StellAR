package com.ssafy.stellar.star.repository;

import com.ssafy.stellar.star.entity.StarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StarRepository extends JpaRepository<StarEntity, String> {
}
