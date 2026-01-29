package com.evergreen.EvergreenAuthServer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.evergreen.EvergreenAuthServer.models.UserActivity;

@Repository
public interface UserActivityRepository extends JpaRepository<UserActivity, Integer> {

}
