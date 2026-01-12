package com.evergreen.EvergreenServer.dtos.requests;


import com.evergreen.EvergreenServer.dtos.entity.AppUserDto;
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
