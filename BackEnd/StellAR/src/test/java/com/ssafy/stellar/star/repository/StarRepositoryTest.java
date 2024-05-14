package com.ssafy.stellar.star.repository;

import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@DataJpaTest
@DisplayName("User Star Bookmark Repository Unit-Test")
public interface StarRepositoryTest {

    @MockBean
    StarRepository STAR_REPOSITORY = null;




}
