package com.evergreen.EvergreenAuthServer.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import com.evergreen.EvergreenAuthServer.models.AppUserModel;
import com.evergreen.lib.dtos.appuser.AppUserDto;


@Mapper(componentModel = "spring")
public interface AppUserMapper {

    AppUserModel toEntity(AppUserDto appUserDto);

    List<AppUserModel> toEntityList(List<AppUserDto> appuserDtos);

    // @Mapping(target = "password", ignore = true) // dont map password
    AppUserDto toDto(AppUserModel appUser);

    List<AppUserDto> toDtoList(List<AppUserModel> appUsers);

}
