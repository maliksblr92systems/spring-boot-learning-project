package com.evergreen.EvergreenAuthServer.dtos.responses;

import com.evergreen.lib.dtos.appuser.AppUserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResponseDto {
    private AppUserDto user;
    private String accessToken;
}
