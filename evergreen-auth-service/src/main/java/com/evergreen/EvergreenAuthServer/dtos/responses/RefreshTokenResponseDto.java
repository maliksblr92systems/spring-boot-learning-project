package com.evergreen.EvergreenAuthServer.dtos.responses;

import lombok.Data;

@Data
public class RefreshTokenResponseDto {

    String accessToken;
    String refreshToken;

}
