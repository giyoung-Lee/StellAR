package com.ssafy.stellar.kakaopayments.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {

    private Integer productId;
    private String productName;
    private String summaryDesc;
    private String desc;
    private String size;
    private Integer taxFreeAmount;

}
