package com.ssafy.stellar.kakaopayments.controller;

import com.google.gson.Gson;
import com.ssafy.stellar.kakaopayments.dto.request.KakaoApproveRequestDto;
import com.ssafy.stellar.kakaopayments.dto.request.PurchaseRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("kakaopay Controller Integration Test")
@ActiveProfiles("mariadb")
public class kakaopayControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("카카오 결제 요청")
    void readyToKakaoPaySuccess() throws Exception {
        KakaoApproveRequestDto dto = new KakaoApproveRequestDto();
        dto.setUserId("test");
        dto.setAmount(1);
        dto.setProductId(1001);

        // Gson 객체 생성
        Gson gson = new Gson();
        String requestBody = gson.toJson(dto);

        mockMvc.perform(
                        post("/payment/ready")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                ).andExpect(status().isOk());
    }

    // /success 요청은 pgToken을 받아올 수가 없기 때문에.. 테스트 불가

    @Test
    @DisplayName("결제 상품 불러오기")
    void returnProductsSuccess() throws Exception {
        mockMvc.perform(get("/payment/products"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("결제 기록 저장")
    void savePaymentHistorySuccess() throws Exception {
        PurchaseRequestDto dto = new PurchaseRequestDto();
        dto.setUserId("test");
        dto.setQuantity(1);
        dto.setTotalPrice(198000);
        dto.setRecipient("받는사람");
        dto.setAddressPost("우편번호");
        dto.setProductId(1001);
        dto.setAddressSummary("도로명주소");
        dto.setAddressDetail("상세 주소");

        Gson gson = new Gson();
        String requestBody = gson.toJson(dto);

        mockMvc.perform(
                post("/payment/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        ).andExpect(status().isOk());

    }

    @Test
    @DisplayName("카카오 결제 요청 실패")
    void readyToKakaoPayFailure() throws Exception {
        // 잘못된 요청 데이터를 생성
        KakaoApproveRequestDto dto = new KakaoApproveRequestDto();
        dto.setUserId(null); // 잘못된 요청: 사용자 ID가 null

        // Gson 객체 생성
        Gson gson = new Gson();
        String requestBody = gson.toJson(dto);

        mockMvc.perform(
                post("/payment/ready")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        ).andExpect(status().isInternalServerError()); // 내부 서버 오류를 기대
    }

}
