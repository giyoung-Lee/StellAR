package com.ssafy.stellar.kakaopayments.service;

import com.ssafy.stellar.kakaopayments.dto.request.KakaoApproveRequestDto;
import com.ssafy.stellar.kakaopayments.dto.request.KakaoSuccessRequestDto;
import com.ssafy.stellar.kakaopayments.dto.request.PurchaseRequestDto;
import com.ssafy.stellar.kakaopayments.dto.response.KakaoApproveResponseDto;
import com.ssafy.stellar.kakaopayments.dto.response.KakaoReadyResponseDto;
import com.ssafy.stellar.kakaopayments.dto.response.ProductDto;
import com.ssafy.stellar.kakaopayments.entity.ProductEntity;
import com.ssafy.stellar.kakaopayments.entity.UserPaymentHistoryEntity;
import com.ssafy.stellar.kakaopayments.repository.ProductRepository;
import com.ssafy.stellar.kakaopayments.repository.UserPaymentHistoryRepository;
import com.ssafy.stellar.user.entity.UserEntity;
import com.ssafy.stellar.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class KakaoPayServiceImpl  implements KakaoPayService {

    static final String cid = "TC0ONETIME"; // 가맹점 테스트 코드

    @Value("${kakaopay_admin-key}")
    private String admin_Key;

    private final ProductRepository  productRepository;
    private final UserPaymentHistoryRepository userPaymentHistoryRepository;
    private final UserRepository userRepository;
    private KakaoReadyResponseDto kakaoReadyResponseDto;
    public KakaoPayServiceImpl(ProductRepository productRepository,
                               UserPaymentHistoryRepository userPaymentHistoryRepository,
                               UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userPaymentHistoryRepository = userPaymentHistoryRepository;
        this.userRepository = userRepository;
    }


    @Override
    public KakaoReadyResponseDto kakaoPayReady(KakaoApproveRequestDto kakaoApproveRequestDto) {
        ProductEntity entity = productRepository.findAllByProductId(kakaoApproveRequestDto.getProductId());

        // 카카오페이 요청 양식
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("partner_order_id", entity.getProductId().toString());
        parameters.add("partner_user_id", kakaoApproveRequestDto.getUserId());
        parameters.add("item_name", entity.getProductName());
        parameters.add("quantity", kakaoApproveRequestDto.getAmount().toString());

        int total_amount = entity.getTaxFreeAmount() * kakaoApproveRequestDto.getAmount();
        parameters.add("total_amount", Integer.toString(total_amount));
        parameters.add("tax_free_amount", entity.getTaxFreeAmount().toString());

        // TODO 실서버
//        // 성공 시 redirect url
//        parameters.add("approval_url", "https://k10c105.p.ssafy.io/order/"+kakaoApproveRequestDto.getUserId()+"/success");
//        // 취소 시 redirect url
//        parameters.add("cancel_url", "https://k10c105.p.ssafy.io/order/"+kakaoApproveRequestDto.getUserId()+"/error");
//        // 실패 시 redirect url
//        parameters.add("fail_url", "https://k10c105.p.ssafy.io/order/"+kakaoApproveRequestDto.getUserId()+"/error");

        // TODO 로컬 서버
        // 성공 시 redirect url
        parameters.add("approval_url", "http://localhost:5173/order/"+kakaoApproveRequestDto.getUserId()+"/success");
        // 취소 시 redirect url
        parameters.add("cancel_url", "http://localhost:5173/order/"+kakaoApproveRequestDto.getUserId()+"/error");
        // 실패 시 redirect url
        parameters.add("fail_url", "http://localhost:5173/order/"+kakaoApproveRequestDto.getUserId()+"/error");

        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/ready",
                requestEntity,
                KakaoReadyResponseDto.class);
    }

    @Override
    public KakaoApproveResponseDto ApproveResponse(KakaoSuccessRequestDto kakaoSuccessRequestDto) {

        // 카카오 요청
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("tid", kakaoSuccessRequestDto.getTid());
        parameters.add("partner_order_id", kakaoSuccessRequestDto.getPartnerOrderId());
        parameters.add("partner_user_id", kakaoSuccessRequestDto.getPartnerUserId());
        parameters.add("pg_token", kakaoSuccessRequestDto.getPgToken());

        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        KakaoApproveResponseDto approveResponse = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/approve",
                requestEntity,
                KakaoApproveResponseDto.class);

        return approveResponse;
    }

    @Override
    public List<ProductDto> reuturnAllproduct() {
        List<ProductEntity> list = productRepository.findAll();
        List<ProductDto> result = new ArrayList<>();

        for(ProductEntity entity : list) {
            ProductDto dto = new ProductDto();
            dto.setProductId(entity.getProductId());
            dto.setProductName(entity.getProductName());
            dto.setSummaryDesc(entity.getSummaryDesc());
            dto.setDesc(entity.getDesc());
            dto.setSize(entity.getSize());
            dto.setTaxFreeAmount(entity.getTaxFreeAmount());

            result.add(dto);
        }
        return result;
    }

    @Override
    public void savePurchase(PurchaseRequestDto purchaseRequestDto) {
        UserPaymentHistoryEntity entity = new UserPaymentHistoryEntity();
        UserEntity userEntity = userRepository.findByUserId(purchaseRequestDto.getUserId());

        entity.setUserId(userEntity);
        entity.setQuantity(purchaseRequestDto.getQuantity());
        entity.setTotalPrice(purchaseRequestDto.getTotalPrice());
        entity.setRecipient(purchaseRequestDto.getRecipient());
        entity.setAddressPost(purchaseRequestDto.getAddressPost());

        ProductEntity productEntity =
                productRepository.findAllByProductId(purchaseRequestDto.getProductId());

        entity.setProductId(productEntity);
        entity.setProductName(productEntity);

        entity.setAddressSummary(purchaseRequestDto.getAddressSummary());
        entity.setAddressDetail(purchaseRequestDto.getAddressDetail());

        userPaymentHistoryRepository.save(entity);
    }

    /**
     * 카카오 요구 헤더값
     */
    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();

        String auth = "KakaoAK " + admin_Key;

        httpHeaders.set("Authorization", auth);
        httpHeaders.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        return httpHeaders;
    }
}