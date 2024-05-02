package com.ssafy.stellar.userConstellation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserConstellationByIdRequestDto {

    @Schema(description = "유저 ID", required = true)
    private String userId;

    @Schema(description = "유저 별자리 ID", required = true)
    private Long userConstellationId;

}
