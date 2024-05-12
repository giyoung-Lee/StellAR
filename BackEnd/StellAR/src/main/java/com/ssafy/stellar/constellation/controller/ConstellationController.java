package com.ssafy.stellar.constellation.controller;

import com.ssafy.stellar.constellation.dto.response.ConstellationDto;
import com.ssafy.stellar.constellation.dto.response.ConstellationEventDto;
import com.ssafy.stellar.constellation.dto.response.ConstellationXODto;
import com.ssafy.stellar.constellation.service.ConstellationService;
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

    // hwangdo13, 3won28su
    @GetMapping("/all")
    public ResponseEntity<?> returnAllConstellation(@RequestParam String constellationType) {
        try {
            List<ConstellationDto> dto = constellationService.findAllConstellation(constellationType);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // hwangdo13, 3won28su
    @GetMapping("/link")
    public ResponseEntity<?> returnConstellation(@RequestParam String constellationType) {
        try {
            Map<String, Object> object = constellationService.findConstellationLink(constellationType);
            return new ResponseEntity<>(object, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/find")
    public ResponseEntity<?> returnConstellationById(@RequestParam String constellationId) {
        try {
            ConstellationDto dto = constellationService.findConstellationById(constellationId);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/event")
    public ResponseEntity<?> returnConstellationEvent() {
        try {
            List<ConstellationEventDto> list = constellationService.returnConstellationEvent();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/xo")
    public ResponseEntity<?> returnContellationXO(@RequestParam String constellationId) {
        try {
            List<ConstellationXODto> dto = constellationService.returnConstellationXO(constellationId);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
