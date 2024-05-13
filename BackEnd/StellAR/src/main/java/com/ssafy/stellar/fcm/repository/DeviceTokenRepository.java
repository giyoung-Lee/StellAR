package com.ssafy.stellar.fcm.repository;

import com.ssafy.stellar.fcm.entity.DeviceTokenEntity;
import com.ssafy.stellar.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceTokenRepository extends JpaRepository<DeviceTokenEntity, String> {
    DeviceTokenEntity findByDeviceTokenAndUser(String deviceToken, UserEntity User);

}
