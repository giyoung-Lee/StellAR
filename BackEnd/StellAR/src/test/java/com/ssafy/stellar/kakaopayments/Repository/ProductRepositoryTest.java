package com.ssafy.stellar.kakaopayments.Repository;


import com.ssafy.stellar.kakaopayments.entity.ProductEntity;
import com.ssafy.stellar.kakaopayments.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Product Repository Unit-Test")
@ActiveProfiles("h2")
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private ProductEntity product;

    @BeforeEach()
    void setUp() {
        product = new ProductEntity();
        product.setProductId(1);
        product.setProductName("Test Product");
        product.setSummaryDesc("Test Summary");
        product.setDesc("Test Description");
        product.setSize("Medium");
        product.setTaxFreeAmount(1000);

        productRepository.save(product);
    }

    @Test
    @DisplayName("Test findAllByProductId")
    void testFindAllByProductId() {
        ProductEntity foundProduct = productRepository.findAllByProductId(product.getProductId());

        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.getProductId()).isEqualTo(product.getProductId());
        assertThat(foundProduct.getProductName()).isEqualTo(product.getProductName());
        assertThat(foundProduct.getSummaryDesc()).isEqualTo(product.getSummaryDesc());
        assertThat(foundProduct.getDesc()).isEqualTo(product.getDesc());
        assertThat(foundProduct.getSize()).isEqualTo(product.getSize());
        assertThat(foundProduct.getTaxFreeAmount()).isEqualTo(product.getTaxFreeAmount());
    }
}
