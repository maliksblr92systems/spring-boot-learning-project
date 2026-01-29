package com.evergreen.EvergreenAuthServer.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.evergreen.EvergreenAuthServer.dtos.entity.AppUserDto;
import com.evergreen.EvergreenAuthServer.models.AppUser;

@Mapper(componentModel = "spring")
public interface AppUserMapper {

    AppUser toEntity(AppUserDto appUserDto);

    List<AppUser> toEntityList(List<AppUserDto> appuserDtos);

    // @Mapping(target = "password", ignore = true) // dont map password
    AppUserDto toDto(AppUser appUser);

    List<AppUserDto> toDtoList(List<AppUser> appUsers);

}
