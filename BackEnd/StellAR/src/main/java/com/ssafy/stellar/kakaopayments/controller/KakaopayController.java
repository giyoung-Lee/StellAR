package com.ssafy.stellar.kakaopayments.controller;

import com.ssafy.stellar.kakaopayments.dto.request.KakaoApproveRequestDto;
import com.ssafy.stellar.kakaopayments.dto.request.KakaoSuccessRequestDto;
import com.ssafy.stellar.kakaopayments.dto.request.PurchaseRequestDto;
import com.ssafy.stellar.kakaopayments.dto.response.KakaoApproveResponseDto;
import com.ssafy.stellar.kakaopayments.dto.response.KakaoReadyResponseDto;
import com.ssafy.stellar.kakaopayments.dto.response.ProductDto;
import com.ssafy.stellar.kakaopayments.service.KakaoPayServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Payments", description = "kakaoPay")
@Slf4j
@RestController
@RequestMapping("/payment")
public class KakaopayController{

    private final KakaoPayServiceImpl kakaoPayService;

    public KakaopayController(KakaoPayServiceImpl kakaoPayService) {
        this.kakaoPayService = kakaoPayService;
    }

    /**
     * 결제요청
     */
    @PostMapping("/ready")
    public ResponseEntity<?> readyToKakaoPay(@RequestBody KakaoApproveRequestDto kakaoApproveRequestDto) {
        try {
            KakaoReadyResponseDto dto = kakaoPayService.kakaoPayReady(kakaoApproveRequestDto);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error occurred while processing readyToKakaoPay request", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/success")
    public ResponseEntity<?> afterPayRequest(@RequestBody KakaoSuccessRequestDto kakaoSuccessRequestDto) {
        try{
            KakaoApproveResponseDto dto = kakaoPayService.ApproveResponse(kakaoSuccessRequestDto);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error occurred while processing afterPayRequest", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/products")
    public ResponseEntity<?> returnProducts() {
        // 상품 리스트를 전부 보내주기.
        try {
            List<ProductDto> dto = kakaoPayService.reuturnAllproduct();
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error occurred while fetching products", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> savePayment(@RequestBody PurchaseRequestDto purchaseRequestDto) {
        try {
            kakaoPayService.savePurchase(purchaseRequestDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error occurred while processing savePayment request", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}