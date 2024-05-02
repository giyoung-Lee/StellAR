package com.ssafy.stellar.userConstellation.entity;

import com.ssafy.stellar.star.entity.StarEntity;
import com.ssafy.stellar.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.sql.ast.tree.expression.Star;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "user_constellation_link")
public class UserConstellationLinkEntity {

    @Id
    @Column(name = "user_link_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userLinkId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_constellation_id")
    private UserConstellationEntity userConstellation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "start_star_id")
    private StarEntity startStar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "end_star_id")
    private StarEntity endStar;

}
