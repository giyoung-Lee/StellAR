package com.ssafy.stellar.kakaopayments.Service;

import com.ssafy.stellar.kakaopayments.dto.request.KakaoApproveRequestDto;
import com.ssafy.stellar.kakaopayments.dto.request.PurchaseRequestDto;
import com.ssafy.stellar.kakaopayments.dto.response.*;
import com.ssafy.stellar.kakaopayments.entity.ProductEntity;
import com.ssafy.stellar.kakaopayments.entity.UserPaymentHistoryEntity;
import com.ssafy.stellar.kakaopayments.repository.ProductRepository;
import com.ssafy.stellar.kakaopayments.repository.UserPaymentHistoryRepository;
import com.ssafy.stellar.kakaopayments.service.KakaoPayServiceImpl;
import com.ssafy.stellar.user.entity.UserEntity;
import com.ssafy.stellar.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
public class KakaopayServiceTest {

    @Autowired
    private KakaoPayServiceImpl kakaoPayService;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserPaymentHistoryRepository userPaymentHistoryRepository;

    @MockBean
    private RestTemplate restTemplate;

    // 이 나쁜놈 때문에 SpringBootTest 로 진행하였음.
    // ExtendWith 을 사용해서 Mock으로 테스트 하려니까 불러올 수가 없음 그냥
    // 이 나쁜놈
    @Value("${kakaopay_admin-key}")
    private String admin_Key;

    @Test
    @DisplayName("Test kakaoPayReady")
    void testKakaoPayReady() {
        // Given
        KakaoApproveRequestDto kakaoApproveRequestDto = new KakaoApproveRequestDto();
        kakaoApproveRequestDto.setUserId("test");
        kakaoApproveRequestDto.setAmount(1);
        kakaoApproveRequestDto.setProductId(1001);

        ProductEntity entity = new ProductEntity();
        entity.setProductId(1001);
        entity.setProductName("[교육용] 에어터치 스크린");
        entity.setSummaryDesc("라즈베리파이를 기반한 에어터치 스크린");
        entity.setDesc("라즈베리파이5를 기반한 초음파 센서를 사용한 에어터치 스크린. 100cm * 75cm 인식가능");
        entity.setSize("100cm * 15cm * 5cm");
        entity.setTaxFreeAmount(198000);

        when(productRepository.findAllByProductId(kakaoApproveRequestDto.getProductId())).thenReturn(entity);

        KakaoReadyResponseDto responseDto = new KakaoReadyResponseDto();
        responseDto.setTid("testTid");
        responseDto.setNext_redirect_mobile_url("testMobileUrl");
        responseDto.setNext_redirect_pc_url("testPcUrl");
        responseDto.setCreated_at("Create_at");

        // Expected request entity
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", "TC0ONETIME");
        parameters.add("partner_order_id", entity.getProductId().toString());
        parameters.add("partner_user_id", kakaoApproveRequestDto.getUserId());
        parameters.add("item_name", entity.getProductName());
        parameters.add("quantity", kakaoApproveRequestDto.getAmount().toString());

        int total_amount = entity.getTaxFreeAmount() * kakaoApproveRequestDto.getAmount();
        parameters.add("total_amount", Integer.toString(total_amount));
        parameters.add("tax_free_amount", entity.getTaxFreeAmount().toString());

        // 성공 시 redirect url
        parameters.add("approval_url", "http://localhost:5173/order/"+kakaoApproveRequestDto.getUserId()+"/success");
        // 취소 시 redirect url
        parameters.add("cancel_url", "http://localhost:5173/order/"+kakaoApproveRequestDto.getUserId()+"/error");
        // 실패 시 redirect url
        parameters.add("fail_url", "http://localhost:5173/order/"+kakaoApproveRequestDto.getUserId()+"/error");

        HttpHeaders httpHeaders = new HttpHeaders();
        String auth = "KakaoAK " + admin_Key;

        httpHeaders.set("Authorization", auth);
        httpHeaders.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> expectedRequestEntity = new HttpEntity<>(parameters, httpHeaders);

        // Mocking the RestTemplate call
        when(restTemplate.postForEntity(
                "https://kapi.kakao.com/v1/payment/approve",
                expectedRequestEntity,
                KakaoReadyResponseDto.class
        )).thenReturn(ResponseEntity.ok(responseDto));

        // When
        KakaoReadyResponseDto actualResponseDto = kakaoPayService.kakaoPayReady(kakaoApproveRequestDto);

        // Then
        assertThat(actualResponseDto).isNotNull();
        assertThat(actualResponseDto.getTid()).isNotNull();
        assertThat(actualResponseDto.getNext_redirect_mobile_url()).isNotNull();
        assertThat(actualResponseDto.getNext_redirect_pc_url()).isNotNull();
    }

    // PgToken을 위의 kakaoReady에서 받은 url을 가지고 스마트폰에서 결제 받아야 받을 수 있음...
    // 그래서 테스트 불가능이라 판단...함...
//    @Test
//    @DisplayName("Test ApproveResponse")
//    void testApproveResponse() {
//        // Given
//        KakaoSuccessRequestDto kakaoSuccessRequestDto = new KakaoSuccessRequestDto();
//        kakaoSuccessRequestDto.setTid("testTid");
//        kakaoSuccessRequestDto.setPgToken("pgToken");
//        kakaoSuccessRequestDto.setPartnerOrderId("1001");
//        kakaoSuccessRequestDto.setPartnerUserId("test");
//
//
//        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
//        parameters.add("cid", "TC0ONETIME");
//        parameters.add("tid", kakaoSuccessRequestDto.getTid());
//        parameters.add("partner_order_id", kakaoSuccessRequestDto.getPartnerOrderId());
//        parameters.add("partner_user_id", kakaoSuccessRequestDto.getPartnerUserId());
//        parameters.add("pg_token", kakaoSuccessRequestDto.getPgToken());
//
//        // 더미 데이터 설정
//        KakaoApproveResponseDto responseDto = new KakaoApproveResponseDto();
//        responseDto.setAid("aid_dummy_value");
//        responseDto.setTid("tid_dummy_value");
//        responseDto.setCid("cid_dummy_value");
//        responseDto.setSid("sid_dummy_value");
//        responseDto.setPartner_order_id("partner_order_id_dummy_value");
//        responseDto.setPartner_user_id("partner_user_id_dummy_value");
//        responseDto.setPayment_method_type("payment_method_type_dummy_value");
//
//        Amount amount = new Amount();
//        amount.setTotal(1000);
//        amount.setTax_free(0);
//        amount.setTax(100);
//        amount.setPoint(0);
//        amount.setDiscount(0);
//        responseDto.setAmount(amount);
//
//        CardInfo cardInfo = new CardInfo();
//        cardInfo.setKakaopay_purchase_corp("purchase_corp_dummy_value");
//        cardInfo.setKakaopay_purchase_corp_code("purchase_corp_code_dummy_value");
//        cardInfo.setKakaopay_issuer_corp("issuer_corp_dummy_value");
//        cardInfo.setKakaopay_issuer_corp_code("issuer_corp_code_dummy_value");
//        cardInfo.setKakaopay_purchase_corp("kakaopay_purchase_corp_dummy_value");
//        cardInfo.setKakaopay_purchase_corp_code("kakaopay_purchase_corp_code_dummy_value");
//        cardInfo.setKakaopay_issuer_corp("kakaopay_issuer_corp_dummy_value");
//        cardInfo.setKakaopay_issuer_corp_code("kakaopay_issuer_corp_code_dummy_value");
//        cardInfo.setBin("bin_dummy_value");
//        cardInfo.setCard_type("card_type_dummy_value");
//        cardInfo.setInstall_month("install_month_dummy_value");
//        cardInfo.setApproved_id("approved_id_dummy_value");
//        cardInfo.setCard_mid("card_mid_dummy_value");
//        cardInfo.setInterest_free_install("interest_free_install_dummy_value");
//        cardInfo.setCard_item_code("card_item_code_dummy_value");
//        responseDto.setCard_info(cardInfo);
//
//        responseDto.setItem_name("item_name_dummy_value");
//        responseDto.setItem_code("item_code_dummy_value");
//        responseDto.setQuantity(1);
//        responseDto.setCreated_at("2024-05-18T12:34:56");
//        responseDto.setApproved_at("2024-05-18T12:35:56");
//        responseDto.setPayload("payload_dummy_value");
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        String auth = "KakaoAK " + admin_Key;
//
//        httpHeaders.set("Authorization", auth);
//        httpHeaders.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//        HttpEntity<MultiValueMap<String, String>> expectedRequestEntity = new HttpEntity<>(parameters, httpHeaders);
//
//        when(restTemplate.postForObject(
//                eq("https://kapi.kakao.com/v1/payment/approve"),
//                eq(expectedRequestEntity),
//                eq(KakaoApproveResponseDto.class)
//        )).thenReturn(responseDto);
//
//        // When
//        KakaoApproveResponseDto result = kakaoPayService.ApproveResponse(kakaoSuccessRequestDto);
//
//        // Then
//        assertThat(result).isNotNull();
//        assertThat(result.getAid()).isEqualTo("aid_dummy_value");
//        assertThat(result.getTid()).isEqualTo("tid_dummy_value");
//
//        verify(restTemplate, times(1)).postForObject(
//                eq("https://kapi.kakao.com/v1/payment/approve"),
//                any(HttpEntity.class),
//                eq(KakaoApproveResponseDto.class)
//        );
//    }

    @Test
    @DisplayName("Test reuturnAllproduct")
    void testReuturnAllproduct() {
        // Given
        ProductEntity product1 = new ProductEntity();
        product1.setProductId(1);
        product1.setProductName("Product1");
        product1.setSummaryDesc("Summary1");
        product1.setDesc("Desc1");
        product1.setSize("Size1");
        product1.setTaxFreeAmount(1000);

        ProductEntity product2 = new ProductEntity();
        product2.setProductId(2);
        product2.setProductName("Product2");
        product2.setSummaryDesc("Summary2");
        product2.setDesc("Desc2");
        product2.setSize("Size2");
        product2.setTaxFreeAmount(2000);

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        // When
        List<ProductDto> result = kakaoPayService.reuturnAllproduct();

        // Then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getProductId()).isEqualTo(1);
        assertThat(result.get(1).getProductId()).isEqualTo(2);

        verify(productRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test savePurchase")
    void testSavePurchase() {
        // Given
        PurchaseRequestDto requestDto = new PurchaseRequestDto();
        requestDto.setUserId("testUser");
        requestDto.setProductId(1);
        requestDto.setQuantity(2);
        requestDto.setTotalPrice(2000);
        requestDto.setRecipient("testRecipient");
        requestDto.setAddressPost("testPost");
        requestDto.setAddressSummary("testSummary");
        requestDto.setAddressDetail("testDetail");

        UserEntity userEntity = new UserEntity();
        userEntity.setUserId("testUser");
        userEntity.setName("Test User");

        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductId(1);
        productEntity.setProductName("Test Product");
        productEntity.setTaxFreeAmount(1000);

        UserPaymentHistoryEntity userPaymentHistoryEntity = new UserPaymentHistoryEntity();
        userPaymentHistoryEntity.setUserId(userEntity);
        userPaymentHistoryEntity.setProductId(productEntity);
        userPaymentHistoryEntity.setQuantity(requestDto.getQuantity());
        userPaymentHistoryEntity.setTotalPrice(requestDto.getTotalPrice());
        userPaymentHistoryEntity.setRecipient(requestDto.getRecipient());
        userPaymentHistoryEntity.setAddressPost(requestDto.getAddressPost());
        userPaymentHistoryEntity.setAddressSummary(requestDto.getAddressSummary());
        userPaymentHistoryEntity.setAddressDetail(requestDto.getAddressDetail());

        when(userRepository.findByUserId(requestDto.getUserId())).thenReturn(userEntity);
        when(productRepository.findAllByProductId(requestDto.getProductId())).thenReturn(productEntity);

        // When
        kakaoPayService.savePurchase(requestDto);

        // Then
        verify(userRepository, times(1)).findByUserId(requestDto.getUserId());
        verify(productRepository, times(1)).findAllByProductId(requestDto.getProductId());
        verify(userPaymentHistoryRepository, times(1)).save(any(UserPaymentHistoryEntity.class));
    }
}
