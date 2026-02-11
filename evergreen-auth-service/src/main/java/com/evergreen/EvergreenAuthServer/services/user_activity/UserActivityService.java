package com.evergreen.EvergreenAuthServer.services.user_activity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.evergreen.EvergreenAuthServer.mappers.UserActivityMapper;
import com.evergreen.EvergreenAuthServer.models.AppUserModel;
import com.evergreen.EvergreenAuthServer.models.UserActivityModel;
import com.evergreen.EvergreenAuthServer.repositories.AppUserRepository;
import com.evergreen.EvergreenAuthServer.repositories.UserActivityRepository;
import com.evergreen.EvergreenAuthServer.security.dtos.CustomUserDetail;
import com.evergreen.lib.dtos.UserActivityDto;

@Service
public class UserActivityService implements IUserActivityService {

    @Autowired
    private UserActivityRepository userActivityRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private UserActivityMapper userActivityMapper;

    public List<UserActivityDto> get() {
        CustomUserDetail userPrincipal = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        AppUserModel appUser = appUserRepository.findById(Integer.parseInt(userPrincipal.getUsername()));
        List<UserActivityModel> userActivityList = userActivityRepository.findByUser(appUser);
        return userActivityMapper.toDtoList(userActivityList);

    }

    // @Transactional(propagation = Propagation.REQUIRES_NEW)
    // public void create(AppUserModel user, UserActivityType type, UserActivityStatus status) {
    // UserActivity userActivity = new UserActivity();
    // userActivity.setUser(user);
    // userActivity.setType(type);
    // userActivity.setStatus(status);
    // userActivityRepository.save(userActivity);

    // }
}
