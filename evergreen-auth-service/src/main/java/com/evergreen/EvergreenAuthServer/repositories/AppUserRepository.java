package com.evergreen.EvergreenAuthServer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.evergreen.EvergreenAuthServer.models.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser,Integer> {

    AppUser findByEmail(String email);
}
