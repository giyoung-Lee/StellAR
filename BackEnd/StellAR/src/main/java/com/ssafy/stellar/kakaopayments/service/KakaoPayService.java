package com.ssafy.stellar.kakaopayments.service;

import com.ssafy.stellar.kakaopayments.dto.request.KakaoApproveRequestDto;
import com.ssafy.stellar.kakaopayments.dto.request.KakaoSuccessRequestDto;
import com.ssafy.stellar.kakaopayments.dto.request.PurchaseRequestDto;
import com.ssafy.stellar.kakaopayments.dto.response.KakaoApproveResponseDto;
import com.ssafy.stellar.kakaopayments.dto.response.KakaoReadyResponseDto;
import com.ssafy.stellar.kakaopayments.dto.response.ProductDto;

import java.util.List;

public interface KakaoPayService {
    KakaoReadyResponseDto kakaoPayReady(KakaoApproveRequestDto kakaoApproveRequestDto);

    KakaoApproveResponseDto ApproveResponse(KakaoSuccessRequestDto kakaoSuccessRequestDto);

    List<ProductDto> reuturnAllproduct();

    void savePurchase(PurchaseRequestDto purchaseRequestDto);
}
