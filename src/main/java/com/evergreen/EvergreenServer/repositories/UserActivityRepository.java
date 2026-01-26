package com.evergreen.EvergreenServer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.evergreen.EvergreenServer.models.UserActivity;

@Repository
public interface UserActivityRepository extends JpaRepository<UserActivity, Integer> {

}
