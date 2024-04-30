package com.ssafy.stellar.userBookmark.dto.request;

import com.ssafy.stellar.userBookmark.entity.UserBookmarkEntity;
import com.ssafy.stellar.star.entity.StarEntity;
import com.ssafy.stellar.user.entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BookmarkRequestDto {


    @Schema(description = "사용자 ID", required = true)
    private String userId;

    @Schema(description = "별 ID", required = true)
    private String starId;

    @Schema(description = "북마크 이름", required = true)
    private String bookmarkName;

}
