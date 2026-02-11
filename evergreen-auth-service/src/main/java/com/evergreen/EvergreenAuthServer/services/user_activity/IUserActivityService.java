package com.evergreen.EvergreenAuthServer.services.user_activity;

import java.util.List;

import com.evergreen.lib.dtos.UserActivityDto;

public interface IUserActivityService {

    public List<UserActivityDto> get();

}
