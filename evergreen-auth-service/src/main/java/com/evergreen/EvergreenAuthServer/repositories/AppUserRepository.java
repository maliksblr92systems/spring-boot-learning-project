package com.evergreen.EvergreenAuthServer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.evergreen.EvergreenAuthServer.models.AppUserModel;

@Repository
public interface AppUserRepository extends JpaRepository<AppUserModel, Integer> {

    AppUserModel findByEmail(String email);
}
