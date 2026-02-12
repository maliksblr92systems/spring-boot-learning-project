package com.evergreen.EvergreenAuthServer.controllers;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evergreen.EvergreenAuthServer.dtos.requests.RegisterUserRequestDto;
import com.evergreen.EvergreenAuthServer.dtos.requests.RegisterUserResponseDto;
import com.evergreen.EvergreenAuthServer.dtos.requests.UserLoginRequestDto;
import com.evergreen.EvergreenAuthServer.dtos.responses.RefreshTokenResponseDto;
import com.evergreen.EvergreenAuthServer.dtos.responses.UserIsAuthenticatedResponseDto;
import com.evergreen.EvergreenAuthServer.dtos.responses.UserLoginResponseDto;
import com.evergreen.EvergreenAuthServer.services.auth.IAuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    @Qualifier("authServiceOpaqueToken")
    private IAuthService authservice;

    @PostMapping("login")
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody @Valid UserLoginRequestDto requestBody, HttpServletRequest request) {
        UserLoginResponseDto response = this.authservice.login(requestBody, request);
        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", response.getRefreshToken()).httpOnly(true).secure(true) // only over HTTPS
                .path("/api/v1/auth/refresh").maxAge(Duration.ofDays(7)).sameSite("Strict").build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, refreshCookie.toString()).body(response);
    }

    @PostMapping("register")
    public ResponseEntity<RegisterUserResponseDto> register(@RequestBody @Valid RegisterUserRequestDto request) {
        RegisterUserResponseDto response = this.authservice.register(request);
        return new ResponseEntity<RegisterUserResponseDto>(response, HttpStatus.OK);
    }

    @GetMapping("/is-authenticated")
    public ResponseEntity<UserIsAuthenticatedResponseDto> isAuthenticated() {
        UserIsAuthenticatedResponseDto response = this.authservice.isAuthenticated();
        return new ResponseEntity<UserIsAuthenticatedResponseDto>(response, HttpStatus.OK);

    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponseDto> refresh(@CookieValue("refreshToken") String refreshToken) {
        RefreshTokenResponseDto response = this.authservice.refresh(refreshToken);
        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", response.getRefreshToken()).httpOnly(true).secure(true) // only over HTTPS
                .path("/api/v1/auth/refresh").maxAge(Duration.ofDays(7)).sameSite("Strict").build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, refreshCookie.toString()).body(response);

    }
}
