package com.ssafy.stellar.userBookmark.entity;

import com.ssafy.stellar.star.entity.StarEntity;
import com.ssafy.stellar.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "user_bookmark")
@Builder
public class UserBookmarkEntity {

    @Id
    @Column(name = "bookmark_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookmarkId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "star_id")
    private StarEntity star;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "bookmark_name")
    private String bookmarkName;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    // 생성시간을 자동으로 설정하는 메서드
    @PrePersist
    protected void onCreate() {
        createTime = LocalDateTime.now();
    }

}
