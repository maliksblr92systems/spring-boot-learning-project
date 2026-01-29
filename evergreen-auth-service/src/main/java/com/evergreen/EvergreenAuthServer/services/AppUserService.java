package com.evergreen.EvergreenAuthServer.services;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.evergreen.EvergreenAuthServer.advices.ApiException;
import com.evergreen.EvergreenAuthServer.dtos.requests.RegisterUserRequestDto;
import com.evergreen.EvergreenAuthServer.dtos.requests.RegisterUserResponseDto;
import com.evergreen.EvergreenAuthServer.dtos.requests.UserLoginRequestDto;
import com.evergreen.EvergreenAuthServer.dtos.responses.UserIsAuthenticatedResponseDto;
import com.evergreen.EvergreenAuthServer.dtos.responses.UserLoginResponseDto;
import com.evergreen.EvergreenAuthServer.mappers.AppUserMapper;
import com.evergreen.EvergreenAuthServer.models.AppUser;
import com.evergreen.EvergreenAuthServer.repositories.AppUserRepository;
import com.evergreen.EvergreenAuthServer.security.JwtService;
import com.evergreen.EvergreenAuthServer.security.dtos.CustomUserDetail;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AppUserMapper appUserMapper;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public UserLoginResponseDto loginUser(UserLoginRequestDto userLoginDto) {
        String password = userLoginDto.getPassword();
        String email = userLoginDto.getEmail();

        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        CustomUserDetail principal = (CustomUserDetail) auth.getPrincipal();
        AppUser appUser = appUserRepository.findByEmail(principal.getUsername());



        String accessToken = jwtService.generateJwtToken(appUser);
        UserLoginResponseDto userLoginResponseDto = new UserLoginResponseDto();
        userLoginResponseDto.setUser(appUserMapper.toDto(appUser));
        userLoginResponseDto.setAccessToken(accessToken);
        return userLoginResponseDto;

    }

    public RegisterUserResponseDto registerUser(RegisterUserRequestDto registerUserRequestDto) {
        String password = registerUserRequestDto.getPassword();
        String confirmPassword = registerUserRequestDto.getConfirmPassword();
        String email = registerUserRequestDto.getEmail();
        if (!Objects.equals(password, confirmPassword)) {
            throw ApiException.badRequest("Password and Confirm Password do not match.");
        }
        AppUser alreadyExistsByEmail = appUserRepository.findByEmail(email);
        if (alreadyExistsByEmail != null) {
            throw ApiException.badRequest("User already exists with  email '" + email + "' .");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        AppUser newAppUser = new AppUser();
        newAppUser.setEmail(email);
        newAppUser.setPassword(encodedPassword);
        newAppUser = this.appUserRepository.save(newAppUser);

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(newAppUser.getEmail(), password));
        if (!authentication.isAuthenticated()) {
            throw ApiException.unAuthenticated("Not authenticated");
        }
        String accessToken = jwtService.generateJwtToken(newAppUser);
        return RegisterUserResponseDto.build(appUserMapper.toDto(newAppUser), accessToken);

    }

    public UserIsAuthenticatedResponseDto isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail userPrincipal = (CustomUserDetail) authentication.getPrincipal();
        AppUser appUser = appUserRepository.findByEmail(userPrincipal.getUsername());
        return new UserIsAuthenticatedResponseDto(appUserMapper.toDto(appUser));



    }

}
