package com.ssafy.stellar.kakaopayments.controller;

import com.ssafy.stellar.kakaopayments.dto.response.KakaoApproveResponseDto;
import com.ssafy.stellar.kakaopayments.dto.response.KakaoReadyResponseDto;
import com.ssafy.stellar.kakaopayments.service.KakaoPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class KakaopayController {

    private final KakaoPayService kakaoPayService;
    /**
     * 결제요청
     */
    @PostMapping("/ready")
    public ResponseEntity<?> readyToKakaoPay() {
        try {
            KakaoReadyResponseDto dto = kakaoPayService.kakaoPayReady();
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/success")
    public ResponseEntity<?> afterPayRequest(@RequestParam("pg_token") String pgToken) {
        kakaoPayService.ApproveResponse(pgToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 결제 진행 중 취소
     */
    @GetMapping("/cancel")
    public ResponseEntity<?> cancel() {
        return new ResponseEntity<>("cancel", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 결제 실패
     */
    @GetMapping("/fail")
    public ResponseEntity<?> fail() {
        return new ResponseEntity<>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}