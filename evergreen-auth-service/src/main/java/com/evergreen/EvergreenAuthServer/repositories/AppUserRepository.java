package com.evergreen.EvergreenAuthServer.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.evergreen.EvergreenAuthServer.models.AppUserModel;

@Repository
public interface AppUserRepository extends JpaRepository<AppUserModel, Integer> {

    Optional<AppUserModel> findByEmail(String email);

}
