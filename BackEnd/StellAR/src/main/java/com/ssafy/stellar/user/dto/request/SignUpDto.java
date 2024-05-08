package com.ssafy.stellar.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto {

    @Schema(description = "사용자 ID", required = true)
    private String userId;

    @Schema(description = "비밀번호", required = true)
    private String password;

    @Schema(description = "사용자 이름", required = false)
    private String name;

    @Schema(description = "성별", required = false)
    private String gender;

}
