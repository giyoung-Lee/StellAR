package com.ssafy.stellar.StructureTest.service;

import com.ssafy.stellar.StructureTest.entity.TestEntity;
import com.ssafy.stellar.StructureTest.repository.TestRepository;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService{

    private final TestRepository testRepository;

    public TestServiceImpl(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @Override
    public void plusUser(Long id, String name, String gender) {
        TestEntity test = new TestEntity();
        test.setId(id);
        test.setName(name);
        test.setGender(gender);

        testRepository.save(test);
    }
}
