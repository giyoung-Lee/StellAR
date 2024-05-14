package com.ssafy.stellar.kakaopayments.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseRequestDto {

    @Schema(description = "유-자 아이디", required = true)
    private String userId;
    @Schema(description = "수량", required = true)
    private Integer quantity;
    @Schema(description = "총 가격", required = true)
    private Integer totalPrice;
    @Schema(description = "받는 사람", required = true)
    private String recipient;
    @Schema(description = "우편 번호", required = true)
    private String addressPost;
    @Schema(description = "상품 번호", required = true)
    private Integer productId;
    @Schema(description = "도로명 주소", required = true)
    private String addressSummary;
    @Schema(description = "상세 주소", required = true)
    private String addressDetail;
}
