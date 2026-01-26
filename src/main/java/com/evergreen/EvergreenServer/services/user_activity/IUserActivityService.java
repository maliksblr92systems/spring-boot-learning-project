package com.evergreen.EvergreenServer.services.user_activity;

import com.evergreen.EvergreenServer.constants.enums.UserActivityStatus;
import com.evergreen.EvergreenServer.constants.enums.UserActivityType;
import com.evergreen.EvergreenServer.models.AppUser;


public interface IUserActivityService {

    public void create(AppUser user, UserActivityType type, UserActivityStatus status);

}
