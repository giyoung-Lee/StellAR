package com.ssafy.stellar.constellation.controller;

import com.ssafy.stellar.constellation.dto.response.ConstellationAllDto;
import com.ssafy.stellar.constellation.service.ConstellationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
            List<ConstellationAllDto> dto = constellationService.findAllConstellation(constellationType);
            return new ResponseEntity<List<ConstellationAllDto>>(dto, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // hwangdo13, 3won28su
    @GetMapping("/link")
    public ResponseEntity<?> returnConstellation(@RequestParam String constellationType) {
        try {

            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
