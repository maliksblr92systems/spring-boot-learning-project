package com.evergreen.EvergreenAuthServer.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.evergreen.EvergreenAuthServer.models.RefreshTokenModel;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenModel, Integer> {

    List<RefreshTokenModel> findByUserIdAndDeviceInfoAndRevokedFalse(Integer userId, String deviceInfo);

    Optional<RefreshTokenModel> findByHash(String hash);

    List<RefreshTokenModel> findByRevokedFalse();

}
