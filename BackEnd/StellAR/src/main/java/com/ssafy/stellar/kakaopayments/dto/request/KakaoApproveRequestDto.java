package com.ssafy.stellar.kakaopayments.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoApproveRequestDto {

    @Schema(description = "유저 ID", required = true)
    private String userId;

    @Schema(description = "구매 개수", required = true)
    private Integer amount;

    @Schema(description = "상품 코드", required = true)
    private Integer productId;

}
