package com.evergreen.EvergreenAuthServer.services.user_activity;

import com.evergreen.EvergreenAuthServer.constants.enums.UserActivityStatus;
import com.evergreen.EvergreenAuthServer.constants.enums.UserActivityType;
import com.evergreen.EvergreenAuthServer.models.AppUser;


public interface IUserActivityService {

    public void create(AppUser user, UserActivityType type, UserActivityStatus status);

}
