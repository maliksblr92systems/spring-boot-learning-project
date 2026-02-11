package com.evergreen.EvergreenAuthServer.services;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.evergreen.EvergreenAuthServer.dtos.requests.RegisterUserRequestDto;
import com.evergreen.EvergreenAuthServer.dtos.requests.RegisterUserResponseDto;
import com.evergreen.EvergreenAuthServer.dtos.requests.UserLoginRequestDto;
import com.evergreen.EvergreenAuthServer.dtos.responses.UserIsAuthenticatedResponseDto;
import com.evergreen.EvergreenAuthServer.dtos.responses.UserLoginResponseDto;
import com.evergreen.EvergreenAuthServer.mappers.AppUserMapper;
import com.evergreen.EvergreenAuthServer.models.AppUserModel;
import com.evergreen.EvergreenAuthServer.repositories.AppUserRepository;
import com.evergreen.EvergreenAuthServer.security.dtos.CustomUserDetail;
import com.evergreen.lib.dtos.appuser.AuthUser;
import com.evergreen.lib.utils.ApiException;
import com.evergreen.lib.utils.JwtUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AppUserService {

    @Value("${jwt.secretKey}")
    private String SECRET_KEY;

    private final AppUserRepository appUserRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AppUserMapper appUserMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public UserLoginResponseDto loginUser(UserLoginRequestDto userLoginDto) {

        String password = userLoginDto.getPassword();
        String email = userLoginDto.getEmail();

        AppUserModel appUser = appUserRepository.findByEmail(email);
        if (appUser == null) {
            throw ApiException.unAuthenticated("User not found " + email + " .");

        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(appUser.getId(), password));
        if (!authentication.isAuthenticated()) {
            throw ApiException.unAuthenticated("Not authenticated");
        }
        AuthUser authUser = new AuthUser(appUser.getId(), appUser.getEmail(), List.of());
        String accessToken = JwtUtils.generateJwtToken2(SECRET_KEY, authUser);

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
        AppUserModel alreadyExistsByEmail = appUserRepository.findByEmail(email);
        if (alreadyExistsByEmail != null) {
            throw ApiException.badRequest("User already exists with  email '" + email + "' .");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        AppUserModel newAppUser = new AppUserModel();
        newAppUser.setEmail(email);
        newAppUser.setPassword(encodedPassword);
        newAppUser = this.appUserRepository.save(newAppUser);

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(newAppUser.getId(), password));
        if (!authentication.isAuthenticated()) {
            throw ApiException.unAuthenticated("Not authenticated");
        }
        AuthUser authUser = new AuthUser(newAppUser.getId(), newAppUser.getEmail(), List.of());

        String accessToken = JwtUtils.generateJwtToken2(SECRET_KEY, authUser);
        return RegisterUserResponseDto.build(appUserMapper.toDto(newAppUser), accessToken);

    }

    public UserIsAuthenticatedResponseDto isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail userPrincipal = (CustomUserDetail) authentication.getPrincipal();
        AppUserModel appUser = appUserRepository.findByEmail(userPrincipal.getUsername());
        return new UserIsAuthenticatedResponseDto(appUserMapper.toDto(appUser));

    }

}
