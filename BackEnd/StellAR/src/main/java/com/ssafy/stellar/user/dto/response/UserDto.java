package com.ssafy.stellar.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {


    @Schema(description = "사용자 ID", required = true)
    private String userId;

    @Schema(description = "사용자 이름", required = true)
    private String name;

    @Schema(description = "성별", required = true)
    private String gender;


}
