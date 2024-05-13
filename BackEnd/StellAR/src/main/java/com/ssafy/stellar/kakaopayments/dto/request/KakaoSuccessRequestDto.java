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
public class KakaoSuccessRequestDto {

    // 가맹점 코드인 cid도 받아야 하지만, 테스트 코드이므로 "TC0ONETIME"로 하드 코딩됨
    // 실제로는 비즈 신청해서 사업자 번호를 입력해야함.

    @Schema(description = "tid, 결제 고유번호. success에서 받았던 tid 입니다.", required = true)
    private String tid;

    @Schema(description = "pgToken. 성공시 파라미터에 붙여서 온다고 하네요", required = true)
    private String pgToken;

    @Schema(description = "유저 이전 주문 product id 와 동일해아합니다.", required = true)
    private String partnerOrderId;

    @Schema(description = "유저 이전 주문 user id 와 동일해아합니다.", required = true)
    private String partnerUserId;


}
