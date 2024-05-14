package com.ssafy.stellar.pieceOfKnowledge.controller;

import com.ssafy.stellar.pieceOfKnowledge.repository.PieceRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "PieceOfKnowledge", description = "조각 지식")
@RestController
@Slf4j
@RequestMapping("/piece")
public class KnowledgeController {

    private final PieceRepository pieceRepository;

    public KnowledgeController(PieceRepository pieceRepository) {
        this.pieceRepository = pieceRepository;
    }

    @Operation(summary = "조각 지식 조회 퀴즈 조회", description = "알고 계셨나요? 조각 퀴즈는 사실 제가 열심히 검색 해서 직접 작성한" +
            "조각 지식이랍니다.")
    @ApiResponse(responseCode = "200", description = "조각 퀴즈 조회..... 성공!")
    @GetMapping("/knowledge")
    public ResponseEntity<?> returnAllPieceKnowledge() {
        return new ResponseEntity<>(pieceRepository.findAll(), HttpStatus.OK);
    }

}
