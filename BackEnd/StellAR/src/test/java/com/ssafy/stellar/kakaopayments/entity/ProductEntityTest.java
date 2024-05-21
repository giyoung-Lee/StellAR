package com.ssafy.stellar.kakaopayments.entity;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Product Entity Unit-Test")
public class ProductEntityTest {
    private ProductEntity entity;

    @BeforeEach()
    void setEntity() {
        entity = new ProductEntity();

        entity.setProductId(1);
        entity.setProductName("Test Product");
        entity.setSummaryDesc("Test Summary");
        entity.setDesc("Test Description");
        entity.setSize("Medium");
        entity.setTaxFreeAmount(1000);
    }

    @Test
    @DisplayName("Product 엔터티 테스트")
    void getEntity() {
        assertThat(entity.getProductId()).isNotNull();
        assertThat(entity.getProductName()).isNotNull();
        assertThat(entity.getSummaryDesc()).isNotNull();
        assertThat(entity.getDesc()).isNotNull();
        assertThat(entity.getSize()).isNotNull();
        assertThat(entity.getTaxFreeAmount()).isNotNull();
    }
}
