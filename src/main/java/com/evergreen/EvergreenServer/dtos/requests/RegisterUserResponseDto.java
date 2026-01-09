package com.evergreen.EvergreenServer.dtos.requests;


import com.evergreen.EvergreenServer.security.dtos.ProtectedAppUserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class RegisterUserResponseDto {
    public ProtectedAppUserDto user;
    public String accessToken;
}
