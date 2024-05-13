package com.ssafy.stellar.kakaopayments.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "product")
public class ProductEntity {

    @Id
    private Integer productId;
    @Column
    private String productName;
    @Column
    private String summaryDesc;
    @Column
    private String desc;
    @Column
    private String size;
    @Column
    private Integer taxFreeAmount;

}
