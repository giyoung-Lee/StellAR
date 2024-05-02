package com.ssafy.stellar.userBookmark.dto.response;

import com.ssafy.stellar.star.entity.StarEntity;
import com.ssafy.stellar.user.entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BookmarkDto {

    @Schema(description = "사용자 ID", required = true)
    private String userId;

    @Schema(description = "별 ID", required = true)
    private String starId;

    @Schema(description = "별마크 이름", required = true)
    private String bookmarkName;

    @Schema(description = "별마크 설정 시간", required = true)
    private LocalDateTime craeteTime;

}
