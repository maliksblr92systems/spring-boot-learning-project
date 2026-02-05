package com.evergreen.EvergreenAuthServer.dtos.requests;


import com.evergreen.lib.dtos.appuser.AppUserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class RegisterUserResponseDto {
    public AppUserDto user;
    public String accessToken;
}
