package com.evergreen.EvergreenAuthServer.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.evergreen.EvergreenAuthServer.models.AppUserModel;
import com.evergreen.EvergreenAuthServer.models.UserActivityModel;

@Repository
public interface UserActivityRepository extends JpaRepository<UserActivityModel, Integer> {

    List<UserActivityModel> findByUser(AppUserModel user);

}
