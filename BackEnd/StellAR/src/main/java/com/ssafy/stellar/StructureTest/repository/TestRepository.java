package com.ssafy.stellar.StructureTest.repository;

import com.ssafy.stellar.StructureTest.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TestRepository extends JpaRepository<TestEntity, Long> {

    TestEntity findByName(String name);

}
