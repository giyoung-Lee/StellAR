package com.ssafy.stellar.kakaopayments.service;

import com.ssafy.stellar.kakaopayments.dto.response.KakaoApproveResponseDto;
import com.ssafy.stellar.kakaopayments.dto.response.KakaoReadyResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

@Service
@RequiredArgsConstructor
@Transactional
public class KakaoPayService {

    static final String cid = "TC0ONETIME"; // 가맹점 테스트 코드

    @Value("${kakaopay_admin-key}")
    private String admin_Key;

    private KakaoReadyResponseDto kakaoReadyResponseDto;

    public KakaoReadyResponseDto kakaoPayReady() {

        // 카카오페이 요청 양식
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        // TODO DB에 있는값으로 설정해주기. 지금은 하드코딩
        parameters.add("partner_order_id", "1001");
        parameters.add("partner_user_id", "test");
        parameters.add("item_name", "빔 프로젝터");
        parameters.add("quantity", "1");
        parameters.add("total_amount", "200000");
        parameters.add("tax_free_amount", "200000");
        parameters.add("approval_url", "https://k10c105.p.ssafy.io/api/payment/success"); // 성공 시 redirect url
        parameters.add("cancel_url", "https://k10c105.p.ssafy.io"); // 취소 시 redirect url
        parameters.add("fail_url", "https://k10c105.p.ssafy.io"); // 실패 시 redirect url

        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/ready",
                requestEntity,
                KakaoReadyResponseDto.class);
    }

    public KakaoApproveResponseDto ApproveResponse(String pgToken) {

        // 카카오 요청
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("tid", kakaoReadyResponseDto.getTid());
        // TODO DB에 있는 값으로 적어주기
        parameters.add("partner_order_id", "1001");
        parameters.add("partner_user_id", "test");
        parameters.add("pg_token", pgToken);

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