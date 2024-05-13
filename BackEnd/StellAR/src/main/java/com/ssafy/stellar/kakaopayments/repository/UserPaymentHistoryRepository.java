package com.ssafy.stellar.kakaopayments.repository;

import com.ssafy.stellar.kakaopayments.entity.UserPaymentHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPaymentHistoryRepository extends JpaRepository<UserPaymentHistoryEntity, Integer> {
}
