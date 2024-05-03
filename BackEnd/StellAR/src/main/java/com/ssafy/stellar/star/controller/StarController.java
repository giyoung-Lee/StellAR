package com.ssafy.stellar.star.controller;


import com.ssafy.stellar.star.dto.response.PlanetDto;
import com.ssafy.stellar.star.service.StarService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Tag(name = "Star", description = "별 관련 API")
@RestController
@Slf4j
@RequestMapping("/star")
public class StarController {

    private final StarService starService;

    public StarController(StarService starService) {
        this.starService = starService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> returnAllStar(@RequestParam String maxMagv) {
        try {
            Map<String, Object> map = starService.returnAllStar(maxMagv);

            return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/planet")
    public ResponseEntity<?> returnPlanet() {
        try {
            List<PlanetDto> list = starService.returnPlanet();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
