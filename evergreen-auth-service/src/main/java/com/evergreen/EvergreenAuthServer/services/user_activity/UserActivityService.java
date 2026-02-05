package com.evergreen.EvergreenAuthServer.services.user_activity;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.evergreen.EvergreenAuthServer.constants.enums.UserActivityStatus;
import com.evergreen.EvergreenAuthServer.constants.enums.UserActivityType;
import com.evergreen.EvergreenAuthServer.models.AppUserModel;
import com.evergreen.EvergreenAuthServer.models.UserActivity;
import com.evergreen.EvergreenAuthServer.repositories.UserActivityRepository;


@Service
public class UserActivityService implements IUserActivityService {
    private final UserActivityRepository userActivityRepository;

    public UserActivityService(UserActivityRepository userActivityRepository) {
        this.userActivityRepository = userActivityRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void create(AppUserModel user, UserActivityType type, UserActivityStatus status) {
        UserActivity userActivity = new UserActivity();
        userActivity.setUser(user);
        userActivity.setType(type);
        userActivity.setStatus(status);
        userActivityRepository.save(userActivity);

    }
}
