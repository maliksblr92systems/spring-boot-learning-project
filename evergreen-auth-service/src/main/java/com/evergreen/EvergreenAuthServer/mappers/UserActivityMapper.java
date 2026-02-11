package com.evergreen.EvergreenAuthServer.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.evergreen.EvergreenAuthServer.models.UserActivityModel;
import com.evergreen.lib.dtos.UserActivityDto;

@Mapper(componentModel = "spring")
public interface UserActivityMapper {

    @Mapping(source = "user.id", target = "userId")
    UserActivityDto toDto(UserActivityModel userActivity);

    List<UserActivityDto> toDtoList(List<UserActivityModel> userActivityList);

}
