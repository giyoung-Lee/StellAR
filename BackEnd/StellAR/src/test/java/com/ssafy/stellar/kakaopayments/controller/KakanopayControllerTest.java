package com.ssafy.stellar.kakaopayments.controller;

import com.google.gson.Gson;
import com.ssafy.stellar.kakaopayments.dto.request.KakaoApproveRequestDto;
import com.ssafy.stellar.kakaopayments.dto.request.KakaoSuccessRequestDto;
import com.ssafy.stellar.kakaopayments.dto.request.PurchaseRequestDto;
import com.ssafy.stellar.kakaopayments.dto.response.*;
import com.ssafy.stellar.kakaopayments.service.KakaoPayServiceImpl;
import com.ssafy.stellar.settings.TestSecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = KakaopayController.class)
@Import(TestSecurityConfig.class)
@ExtendWith(MockitoExtension.class)
@DisplayName("Kakaopay Controller Unit-Test")
public class KakanopayControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KakaoPayServiceImpl kakaoPayService;

    @Test
    @DisplayName("Test readyToKakaoPay")
    public void testReadyToKakaoPay() throws Exception {
        // Given
        KakaoReadyResponseDto responseDto = new KakaoReadyResponseDto();
        responseDto.setTid("Tid");
        responseDto.setCreated_at("Create_at");
        responseDto.setNext_redirect_mobile_url("mobile_url");
        responseDto.setNext_redirect_pc_url("pc_url");

        // When
        when(kakaoPayService.kakaoPayReady(any(KakaoApproveRequestDto.class))).thenReturn(responseDto);

        Gson gson = new Gson();
        KakaoApproveRequestDto requestDto = new KakaoApproveRequestDto();
        requestDto.setUserId("test");
        requestDto.setAmount(1);
        requestDto.setProductId(1001);

        String jsonRequest = gson.toJson(requestDto);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.post("/payment/ready")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tid").value("Tid"))
                .andExpect(jsonPath("$.created_at").value("Create_at"))
                .andExpect(jsonPath("$.next_redirect_mobile_url").value("mobile_url"))
                .andExpect(jsonPath("$.next_redirect_pc_url").value("pc_url"));
    }


    @Test
    @DisplayName("Test afterPayRequest")
    public void testAfterPayRequest() throws Exception {
        // Given
        KakaoApproveResponseDto responseDto = new KakaoApproveResponseDto();
        responseDto.setAid("Aid");
        responseDto.setTid("Tid");
        responseDto.setCid("TC0ONETIME");
        responseDto.setPartner_order_id("order123");
        responseDto.setPartner_user_id("user123");
        responseDto.setPayment_method_type("CARD");
        Amount amount = new Amount();
        amount.setTotal(1000);
        responseDto.setAmount(amount);
        CardInfo cardInfo = new CardInfo();
        cardInfo.setKakaopay_purchase_corp("Corp");
        responseDto.setCard_info(cardInfo);
        responseDto.setItem_name("item");
        responseDto.setQuantity(1);
        responseDto.setCreated_at("2022-01-01T00:00:00Z");
        responseDto.setApproved_at("2022-01-01T00:00:00Z");

        // When
        when(kakaoPayService.ApproveResponse(any(KakaoSuccessRequestDto.class))).thenReturn(responseDto);

        Gson gson = new Gson();
        KakaoSuccessRequestDto requestDto = new KakaoSuccessRequestDto();
        requestDto.setTid("Tid");
        requestDto.setPgToken("test_token");
        requestDto.setPartnerOrderId("order123");
        requestDto.setPartnerUserId("user123");

        String jsonRequest = gson.toJson(requestDto);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.post("/payment/success")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.aid").value("Aid"))
                .andExpect(jsonPath("$.tid").value("Tid"))
                .andExpect(jsonPath("$.cid").value("TC0ONETIME"))
                .andExpect(jsonPath("$.partner_order_id").value("order123"))
                .andExpect(jsonPath("$.partner_user_id").value("user123"))
                .andExpect(jsonPath("$.payment_method_type").value("CARD"))
                .andExpect(jsonPath("$.amount.total").value(1000))
                .andExpect(jsonPath("$.card_info.kakaopay_purchase_corp").value("Corp"))
                .andExpect(jsonPath("$.item_name").value("item"))
                .andExpect(jsonPath("$.quantity").value(1))
                .andExpect(jsonPath("$.created_at").value("2022-01-01T00:00:00Z"))
                .andExpect(jsonPath("$.approved_at").value("2022-01-01T00:00:00Z"));
    }
    @Test
    @DisplayName("Test returnProducts")
    public void testReturnProducts() throws Exception {
        ProductDto product1 = new ProductDto();
        ProductDto product2 = new ProductDto();
        // Set product properties as needed

        List<ProductDto> productList = Arrays.asList(product1, product2);
        when(kakaoPayService.reuturnAllproduct()).thenReturn(productList);

        mockMvc.perform(MockMvcRequestBuilders.get("/payment/products")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1]").exists());
    }

    @Test
    @DisplayName("Test savePayment")
    public void testSavePayment() throws Exception {
        Mockito.doNothing().when(kakaoPayService).savePurchase(any(PurchaseRequestDto.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/payment/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"someField\":\"someValue\"}")) // Replace with actual JSON request body
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test readyToKakaoPay failure")
    public void testReadyToKakaoPayFailure() throws Exception {
        // Given
        doThrow(new RuntimeException("Service exception")).when(kakaoPayService).kakaoPayReady(any(KakaoApproveRequestDto.class));

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/payment/ready")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":\"test\",\"amount\":1,\"productId\":1001}"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Test afterPayRequest failure")
    public void testAfterPayRequestFailure() throws Exception {
        // Given
        doThrow(new RuntimeException("Service exception")).when(kakaoPayService).ApproveResponse(any(KakaoSuccessRequestDto.class));

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/payment/success")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"tid\":\"Tid\",\"pgToken\":\"test_token\"," +
                                "\"partnerOrderId\":\"order123\",\"partnerUserId\":\"user123\"}"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Test returnProducts failure")
    public void testReturnProductsFailure() throws Exception {
        // Given
        doThrow(new RuntimeException("Service exception")).when(kakaoPayService).reuturnAllproduct();

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/payment/products")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Test savePayment failure")
    public void testSavePaymentFailure() throws Exception {
        // Given
        doThrow(new RuntimeException("Service exception")).when(kakaoPayService)
                .savePurchase(any(PurchaseRequestDto.class));

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/payment/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":\"test\",\"productId\":1001,\"amount\":1}"))
                .andExpect(status().isInternalServerError());
    }

}
