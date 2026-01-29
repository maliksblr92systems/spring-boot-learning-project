package com.evergreen.EvergreenAuthServer.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evergreen.EvergreenAuthServer.dtos.requests.RegisterUserRequestDto;
import com.evergreen.EvergreenAuthServer.dtos.requests.RegisterUserResponseDto;
import com.evergreen.EvergreenAuthServer.dtos.requests.UserLoginRequestDto;
import com.evergreen.EvergreenAuthServer.dtos.responses.UserIsAuthenticatedResponseDto;
import com.evergreen.EvergreenAuthServer.dtos.responses.UserLoginResponseDto;
import com.evergreen.EvergreenAuthServer.services.AppUserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AppUserService appUserService;

    public AuthController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping("login")
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody @Valid UserLoginRequestDto request) {
        UserLoginResponseDto response = this.appUserService.loginUser(request);
        return new ResponseEntity<UserLoginResponseDto>(response, HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<RegisterUserResponseDto> register(@RequestBody @Valid RegisterUserRequestDto request) {
        RegisterUserResponseDto response = this.appUserService.registerUser(request);
        return new ResponseEntity<RegisterUserResponseDto>(response, HttpStatus.OK);
    }

    @GetMapping("/is-authenticated")
    public ResponseEntity<UserIsAuthenticatedResponseDto> isAuthenticated() {
        UserIsAuthenticatedResponseDto response = this.appUserService.isAuthenticated();
        return new ResponseEntity<UserIsAuthenticatedResponseDto>(response, HttpStatus.OK);

    }
}
