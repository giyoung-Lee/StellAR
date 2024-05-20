package com.ssafy.stellar.kakaopayments.entity;
import com.ssafy.stellar.user.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("UserPaymentHistory Entity Unit-Test")
public class UserPaymentHistoryEntityTest {

    private UserPaymentHistoryEntity entity;
    private UserEntity userEntity;
    private ProductEntity productEntity;

    @BeforeEach
    void setEntity() {
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
        entity = new UserPaymentHistoryEntity();
        entity.setPaymentId(1);
        entity.setUserId(userEntity);
        entity.setQuantity(2);
        entity.setTotalPrice(2000);
        entity.setRecipient("testRecipient");
        entity.setAddressPost("testPost");
        entity.setProductId(productEntity);
        entity.setProductName(productEntity);
        entity.setAddressSummary("testSummary");
        entity.setAddressDetail("testDetail");
    }

    @Test
    @DisplayName("UserPaymentHistory 엔터티 테스트")
    void getEntity() {
        assertThat(entity.getPaymentId()).isNotNull();
        assertThat(entity.getUserId()).isNotNull();
        assertThat(entity.getQuantity()).isNotNull();
        assertThat(entity.getTotalPrice()).isNotNull();
        assertThat(entity.getRecipient()).isNotNull();
        assertThat(entity.getAddressPost()).isNotNull();
        assertThat(entity.getProductId()).isNotNull();
        assertThat(entity.getProductName()).isNotNull();
        assertThat(entity.getAddressSummary()).isNotNull();
        assertThat(entity.getAddressDetail()).isNotNull();

        assertThat(entity.getUserId().getUserId()).isEqualTo("testUser");
        assertThat(entity.getProductId().getProductId()).isEqualTo(1);
    }
}
