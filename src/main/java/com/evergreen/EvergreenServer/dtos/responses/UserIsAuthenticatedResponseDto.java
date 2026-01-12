package com.evergreen.EvergreenServer.dtos.responses;

import com.evergreen.EvergreenServer.dtos.entity.AppUserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserIsAuthenticatedResponseDto {
    AppUserDto user;
}
