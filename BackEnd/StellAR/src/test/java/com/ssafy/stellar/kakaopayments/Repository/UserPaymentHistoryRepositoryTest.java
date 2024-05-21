package com.ssafy.stellar.kakaopayments.Repository;

import com.ssafy.stellar.kakaopayments.entity.ProductEntity;
import com.ssafy.stellar.kakaopayments.entity.UserPaymentHistoryEntity;
import com.ssafy.stellar.kakaopayments.repository.UserPaymentHistoryRepository;
import com.ssafy.stellar.user.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("UserPayment History Repository Unit-Test")
@ActiveProfiles("h2")
public class UserPaymentHistoryRepositoryTest {

    @Autowired
    private UserPaymentHistoryRepository userPaymentHistoryRepository;

    private UserEntity userEntity;
    private ProductEntity productEntity;
    private UserPaymentHistoryEntity userPaymentHistoryEntity;

    @BeforeEach
    void setUp() {
        // UserEntity 초기화
        userEntity = new UserEntity();
        userEntity.setUserId("testUser");
        userEntity.setPassword("testPassword");
        userEntity.setName("testName");
        userEntity.setGender("testGender");

        // ProductEntity 초기화
        productEntity = new ProductEntity();
        productEntity.setProductId(1);
        productEntity.setProductName("Test Product");
        productEntity.setSummaryDesc("Test Summary");
        productEntity.setDesc("Test Description");
        productEntity.setSize("Medium");
        productEntity.setTaxFreeAmount(1000);

        // UserPaymentHistoryEntity 초기화
        userPaymentHistoryEntity = new UserPaymentHistoryEntity();
        userPaymentHistoryEntity.setUserId(userEntity);
        userPaymentHistoryEntity.setQuantity(2);
        userPaymentHistoryEntity.setTotalPrice(2000);
        userPaymentHistoryEntity.setRecipient("testRecipient");
        userPaymentHistoryEntity.setAddressPost("testPost");
        userPaymentHistoryEntity.setProductId(productEntity);
        userPaymentHistoryEntity.setProductName(productEntity);
        userPaymentHistoryEntity.setAddressSummary("testSummary");
        userPaymentHistoryEntity.setAddressDetail("testDetail");
    }

    @Test
    @DisplayName("Test save UserPaymentHistoryEntity")
    void testSaveUserPaymentHistoryEntity() {
        // When
        UserPaymentHistoryEntity savedEntity = userPaymentHistoryRepository.save(userPaymentHistoryEntity);

        // Then
        assertThat(savedEntity).isNotNull();
        assertThat(savedEntity.getPaymentId()).isNotNull();
        assertThat(savedEntity.getUserId()).isNotNull();
        assertThat(savedEntity.getQuantity()).isEqualTo(2);
        assertThat(savedEntity.getTotalPrice()).isEqualTo(2000);
        assertThat(savedEntity.getRecipient()).isEqualTo("testRecipient");
        assertThat(savedEntity.getAddressPost()).isEqualTo("testPost");
        assertThat(savedEntity.getProductId()).isNotNull();
        assertThat(savedEntity.getProductName()).isNotNull();
        assertThat(savedEntity.getAddressSummary()).isEqualTo("testSummary");
        assertThat(savedEntity.getAddressDetail()).isEqualTo("testDetail");

        assertThat(savedEntity.getUserId().getUserId()).isEqualTo("testUser");
        assertThat(savedEntity.getProductId().getProductId()).isEqualTo(1);
    }
}
