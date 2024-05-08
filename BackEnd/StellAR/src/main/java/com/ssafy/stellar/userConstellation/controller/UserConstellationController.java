package com.ssafy.stellar.userConstellation.controller;

import com.ssafy.stellar.userConstellation.dto.request.UserConstellationRequestDto;
import com.ssafy.stellar.userConstellation.dto.response.UserConstellationDto;
import com.ssafy.stellar.userConstellation.service.UserConstellationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "userConstellation", description = "유저 별자리와 관련된 문서입니다.")
@RestController
@Slf4j
@RequestMapping("/user-constellation")
public class UserConstellationController {

    private final UserConstellationService userConstellationService;

    public UserConstellationController(UserConstellationService userConstellationService) {
        this.userConstellationService = userConstellationService;
    }

    @Operation(summary = "유저 별자리 저장/수정", description = "POST: 별자리를 저장합니다. / PUT: 별자리를 수정합니다.")
    @ApiResponse(responseCode = "200", description = "별자리 수정 성공")
    @ApiResponse(responseCode = "201", description = "별자리 저장 성공")
    @ApiResponse(responseCode = "400", description = "별자리 수정/저장 실패")
    @RequestMapping(path = "/create", method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<?> manageUserConstellation(@RequestBody UserConstellationRequestDto userConstellationRequestDto, HttpServletRequest request) {
        System.out.println("userConstellationRequestDto = " + userConstellationRequestDto);
        try {
            boolean isUpdate = request.getMethod().equals("PUT");

            userConstellationService.manageUserConstellation(userConstellationRequestDto, isUpdate);
            HttpStatusCode status;

            if (isUpdate) {
                status = HttpStatus.OK;
            } else {
                status = HttpStatus.CREATED;
            }

            return new ResponseEntity<Void>(status);
        } catch (IllegalStateException | IllegalArgumentException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "유저 별자리 개별 조회", description = "사용자가 저장한 개별 별자리를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "별자리 조회 성공",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserConstellationDto.class)
            )
    )
    @ApiResponse(responseCode = "400", description = "잘못된 데이터")
    @GetMapping
    public ResponseEntity<?> getUserConstellationById(@RequestParam String userId, @RequestParam Long constellationId) {
        try {
            UserConstellationDto userConstellations = userConstellationService.getUserConstellationById(userId, constellationId);
            return new ResponseEntity<UserConstellationDto>(userConstellations, HttpStatus.OK);
        } catch (UsernameNotFoundException e) {

            log.error("User not found", e);
            return new ResponseEntity<String>("User not found", HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            log.error("Internal server error", e);
            return new ResponseEntity<String>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "유저 별자리 조회", description = "사용자가 저장한 별자리를 조회합니다. 별자리 리스트 출력")
    @ApiResponse(responseCode = "200", description = "별자리 조회 성공",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = UserConstellationDto.class))
            )
    )
    @ApiResponse(responseCode = "404", description = "유저 정보 없음")
    @GetMapping("/all")
    public ResponseEntity<?> getUserConstellation(@RequestParam String userId) {
        try {
            List<UserConstellationDto> userConstellations = userConstellationService.getUserConstellation(userId);
            return new ResponseEntity<List<UserConstellationDto>>(userConstellations, HttpStatus.OK);
        } catch (UsernameNotFoundException e) {

            log.error("User not found", e);
            return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            log.error("Internal server error", e);
            return new ResponseEntity<String>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "유저 별자리 링크 조회", description = "사용자가 저장한 별자리 연결선을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = UserConstellationDto.class))
            )
    )
    @ApiResponse(responseCode = "404", description = "유저 정보 없음")
    @GetMapping("/link")
    public ResponseEntity<?> getUserConstellationLink(@RequestParam String userId) {
        try {
            Map<String, Object> userConstellationsLink = userConstellationService.getUserConstellationLink(userId);
            return new ResponseEntity<>(userConstellationsLink, HttpStatus.OK);
        } catch (UsernameNotFoundException e) {

            log.error("User not found", e);
            return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            log.error("Internal server error", e);
            return new ResponseEntity<String>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Operation(summary = "별마크 삭제", description = "사용자가 저장한 별자리를 삭제합니다.")
    @ApiResponse(responseCode = "204", description = "별자리 삭제")
    @ApiResponse(responseCode = "400", description = "요청 데이터 에러")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUserConstellation(@RequestParam String userId, @RequestParam Long constellationId) {
        try {
            userConstellationService.deleteUserConstellation(userId, constellationId);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<String>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
