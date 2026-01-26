package com.evergreen.EvergreenServer.services.user_activity;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.evergreen.EvergreenServer.constants.enums.UserActivityStatus;
import com.evergreen.EvergreenServer.constants.enums.UserActivityType;
import com.evergreen.EvergreenServer.models.AppUser;
import com.evergreen.EvergreenServer.models.UserActivity;
import com.evergreen.EvergreenServer.repositories.UserActivityRepository;


@Service
public class UserActivityService implements IUserActivityService {
    private final UserActivityRepository userActivityRepository;

    public UserActivityService(UserActivityRepository userActivityRepository) {
        this.userActivityRepository = userActivityRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void create(AppUser user, UserActivityType type, UserActivityStatus status) {
        UserActivity userActivity = new UserActivity();
        userActivity.setUser(user);
        userActivity.setType(type);
        userActivity.setStatus(status);
        userActivityRepository.save(userActivity);

    }
}
