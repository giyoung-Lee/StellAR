package com.ssafy.stellar.userConstellation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserConstellationRequestDto {

    @Schema(description = "유저 ID", required = true)
    private String userId;

    @Schema(description = "유저 ID")
    private Long constellationId;

    @Schema(description = "유저 별자리 이름", required = true)
    private String name;

    @Schema(description = "유저 별자리 설명", required = true)
    private String description;

    @Schema(description = "유저 별자리 선", required = true)
    private List<List<String>> links;
}
