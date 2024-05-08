package com.ssafy.stellar.userConstellation.entity;

import com.ssafy.stellar.star.entity.StarEntity;
import com.ssafy.stellar.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "user_constellation")
public class UserConstellationEntity {

    @Id
    @Column(name = "user_constellation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userConstellationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "creation_date")
    private LocalDateTime createDateTime;

    // 생성시간을 자동으로 설정하는 메서드
    @PrePersist
    @PreUpdate
    protected void onCreate() {
        createDateTime = LocalDateTime.now();
    }

}
