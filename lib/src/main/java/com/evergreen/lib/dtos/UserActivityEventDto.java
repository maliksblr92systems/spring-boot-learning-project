package com.evergreen.lib.dtos;

import com.evergreen.lib.constants.enums.UserActivityStatus;
import com.evergreen.lib.constants.enums.UserActivityType;
import com.evergreen.lib.dtos.appuser.AuthUser;

public record UserActivityEventDto(UserActivityType type, UserActivityStatus status, AuthUser user) {
}
