package com.evergreen.EvergreenAuthServer.services.auth;

import com.evergreen.EvergreenAuthServer.dtos.requests.RegisterUserRequestDto;
import com.evergreen.EvergreenAuthServer.dtos.requests.RegisterUserResponseDto;
import com.evergreen.EvergreenAuthServer.dtos.requests.UserLoginRequestDto;
import com.evergreen.EvergreenAuthServer.dtos.responses.RefreshTokenResponseDto;
import com.evergreen.EvergreenAuthServer.dtos.responses.UserIsAuthenticatedResponseDto;
import com.evergreen.EvergreenAuthServer.dtos.responses.UserLoginResponseDto;

import jakarta.servlet.http.HttpServletRequest;

public interface IAuthService {
    public UserLoginResponseDto login(UserLoginRequestDto userLoginDto, HttpServletRequest request);

    public RegisterUserResponseDto register(RegisterUserRequestDto registerUserRequestDto);

    public UserIsAuthenticatedResponseDto isAuthenticated();

    public RefreshTokenResponseDto refresh(String rawRefreshToken);

}
