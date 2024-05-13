package com.ssafy.stellar.kakaopayments.entity;

import com.ssafy.stellar.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "user_payment_history")
public class UserPaymentHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userId;

    @Column
    private Integer quantity;
    @Column
    private Integer totalPrice;
    @Column
    private String recipient;
    @Column
    private String addressPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_name")
    private ProductEntity productName;
    @Column
    private String addressSummary;
    @Column
    private String addressDetail;
}
