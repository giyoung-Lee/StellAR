package com.ssafy.stellar.constellation.controller;

import com.ssafy.stellar.constellation.dto.response.ConstellationDto;
import com.ssafy.stellar.constellation.dto.response.ConstellationEventDto;
import com.ssafy.stellar.constellation.dto.response.ConstellationXODto;
import com.ssafy.stellar.constellation.service.ConstellationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "Constellation", description = "별자리 관련 API")
@RestController
@Slf4j
@RequestMapping("/constellation")
public class ConstellationController {

    @Autowired
    private final ConstellationService constellationService;

    public ConstellationController(ConstellationService constellationService) {
        this.constellationService = constellationService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ConstellationDto>> returnAllConstellation(@RequestParam String constellationType) {
        try {
            List<ConstellationDto> dto = constellationService.findAllConstellation(constellationType);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while fetching all constellations", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/link")
    public ResponseEntity<Map<String, Object>> returnConstellation(@RequestParam String constellationType) {
        try {
            Map<String, Object> object = constellationService.findConstellationLink(constellationType);
            return new ResponseEntity<>(object, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while fetching constellation link", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/find")
    public ResponseEntity<ConstellationDto> returnConstellationById(@RequestParam String constellationId) {
        try {
            ConstellationDto dto = constellationService.findConstellationById(constellationId);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while fetching constellation by id", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/event")
    public ResponseEntity<List<ConstellationEventDto>> returnConstellationEvent() {
        try {
            List<ConstellationEventDto> list = constellationService.returnConstellationEvent();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while fetching constellation events", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "ox 퀴즈 조회", description = "별자리에 맞는 ox퀴즈를 반환해줍니다. ox가 아니라 xo로 조금 힙하게 해봤어요." +
            " \n 그냥 빈값넣으면 전체 퀴즈가 조회됩니다. ")
    @ApiResponse(responseCode = "200", description = "퀴즈 조회 성공")
    @GetMapping("/xo")
    public ResponseEntity<List<ConstellationXODto>> returnContellationXO(@RequestParam String constellationId) {
        try {
            List<ConstellationXODto> dto = constellationService.returnConstellationXO(constellationId);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while fetching constellation XO", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
