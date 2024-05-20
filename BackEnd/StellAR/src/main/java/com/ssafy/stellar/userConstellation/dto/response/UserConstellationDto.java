package com.ssafy.stellar.userConstellation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserConstellationDto {


    @Schema(description = "유저 별자리 ID")
    private Long userConstellationId;

    @Schema(description = "유저 별자리 이름")
    private String name;

    @Schema(description = "유저 별자리 설명")
    private String description;

    @Schema(description = "유저 별자리 생성 시간")
    private LocalDateTime createTime;

    @Schema(description = "유저 별자리 선")
    private List<UserConstellationLinkDto> links;

    @Schema(description = "유저 별자리 RA(시간기준)", required = true)
    private double hourRA;

    @Schema(description = "유저 별자리 DEC(도기준)", required = true)
    private double degreeDEC;

    @Schema(description = "별위치 정규화", required = true)
    private double nomalizedMagV;

}
