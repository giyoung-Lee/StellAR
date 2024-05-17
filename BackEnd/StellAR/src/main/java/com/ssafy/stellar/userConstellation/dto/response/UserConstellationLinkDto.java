package com.ssafy.stellar.userConstellation.dto.response;

import com.ssafy.stellar.userConstellation.entity.UserConstellationLinkEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserConstellationLinkDto {

    @Schema(description = "유저 별자리 ID")
    private Long userConstellationId;

    @Schema(description = "시작 별")
    private String startStar;

    @Schema(description = "끝 별")
    private String endStar;

}
