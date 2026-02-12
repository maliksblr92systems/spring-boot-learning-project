package com.evergreen.EvergreenAuthServer.services.auth;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
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
import com.evergreen.EvergreenAuthServer.dtos.responses.RefreshTokenResponseDto;
import com.evergreen.EvergreenAuthServer.dtos.responses.UserIsAuthenticatedResponseDto;
import com.evergreen.EvergreenAuthServer.dtos.responses.UserLoginResponseDto;
import com.evergreen.EvergreenAuthServer.mappers.AppUserMapper;
import com.evergreen.EvergreenAuthServer.models.AppUserModel;
import com.evergreen.EvergreenAuthServer.models.RefreshTokenModel;
import com.evergreen.EvergreenAuthServer.repositories.AppUserRepository;
import com.evergreen.EvergreenAuthServer.repositories.RefreshTokenRepository;
import com.evergreen.EvergreenAuthServer.security.dtos.CustomUserDetail;
import com.evergreen.lib.dtos.appuser.AuthUser;
import com.evergreen.lib.utils.ApiException;
import com.evergreen.lib.utils.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthServiceOpaqueToken implements IAuthService {

    @Value("${jwt.accessToken.secretKey}")
    private String ACCESS_TOKEN_SECRET;

    @Value("${jwt.accessToken.expiry}")
    private String ACCESS_TOKEN_EXPIRY;

    @Value("${jwt.refreshToken.secretKey}")
    private String REFRESH_TOKEN_SECRET;

    @Value("${jwt.refreshToken.expiry}")
    private String REFRESH_TOKEN_EXPIRY;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AppUserMapper appUserMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    public UserLoginResponseDto login(UserLoginRequestDto userLoginDto, HttpServletRequest request) {
        String password = userLoginDto.getPassword();
        String email = userLoginDto.getEmail();

        AppUserModel appUser = appUserRepository.findByEmail(email).orElseThrow(() -> ApiException.unAuthenticated("User not found " + email + " ."));
        // create DAO authentication and set to context
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(appUser.getId(), password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // create access and refresh token
        AuthUser authUser = new AuthUser(appUser.getId(), appUser.getEmail(), List.of());
        String accessToken = JwtUtils.generateAccessToken(ACCESS_TOKEN_SECRET, ACCESS_TOKEN_EXPIRY, authUser);
        // user already has session
        String refreshToken = getOpaqueRefreshToken(request, appUser.getId());

        UserLoginResponseDto userLoginResponseDto = new UserLoginResponseDto();
        userLoginResponseDto.setUser(appUserMapper.toDto(appUser));
        userLoginResponseDto.setAccessToken(accessToken);
        userLoginResponseDto.setRefreshToken(refreshToken);

        return userLoginResponseDto;

    }

    public RegisterUserResponseDto register(RegisterUserRequestDto registerUserRequestDto) {
        String password = registerUserRequestDto.getPassword();
        String confirmPassword = registerUserRequestDto.getConfirmPassword();
        String email = registerUserRequestDto.getEmail();
        if (!Objects.equals(password, confirmPassword)) {
            throw ApiException.badRequest("Password and Confirm Password do not match.");
        }
        // already exists check
        appUserRepository.findByEmail(email).orElseThrow(() -> ApiException.badRequest("User already exists with  email '" + email + "' ."));

        AppUserModel newAppUser = new AppUserModel();
        newAppUser.setEmail(email);
        newAppUser.setPassword(bCryptPasswordEncoder.encode(password));
        newAppUser = appUserRepository.save(newAppUser);

        // Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(newAppUser.getId(), password));
        // if (!authentication.isAuthenticated()) {
        // throw ApiException.unAuthenticated("Not authenticated");
        // }
        // AuthUser authUser = new AuthUser(newAppUser.getId(), newAppUser.getEmail(), List.of());

        // String accessToken = JwtUtils.generateAccessToken(ACCESS_TOKEN_SECRET, ACCESS_TOKEN_EXPIRY, authUser);
        // return RegisterUserResponseDto.build(appUserMapper.toDto(newAppUser), accessToken);
        return RegisterUserResponseDto.build("User registered succcessfully.");

    }

    public UserIsAuthenticatedResponseDto isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail userPrincipal = (CustomUserDetail) authentication.getPrincipal();
        AppUserModel appUser = appUserRepository.findByEmail(userPrincipal.getUsername()).orElseThrow(() -> ApiException.unAuthenticated("User not found."));
        return new UserIsAuthenticatedResponseDto(appUserMapper.toDto(appUser));

    }

    public RefreshTokenResponseDto refresh(String rawRefreshToken) {
        List<RefreshTokenModel> allValidTokens = refreshTokenRepository.findByRevokedFalse();
        final RefreshTokenModel matchedRefreshToken = allValidTokens.stream().filter((token) -> bCryptPasswordEncoder.matches(rawRefreshToken, token.getHash())).findFirst()
                .orElseThrow(() -> ApiException.unAuthenticated("Invalid refresh token"));
        if (matchedRefreshToken.getExpiry().isBefore(Instant.now())) {
            throw ApiException.unAuthenticated("Expired refresh token.");
        } else {
            AppUserModel appUser = appUserRepository.findById(matchedRefreshToken.getUserId()).orElseThrow(() -> ApiException.unAuthenticated("User not found."));
            String accessToken = JwtUtils.generateAccessToken(ACCESS_TOKEN_SECRET, ACCESS_TOKEN_EXPIRY, new AuthUser(appUser.getId(), appUser.getEmail(), List.of()));
            RefreshTokenResponseDto responseDto = new RefreshTokenResponseDto();
            responseDto.setAccessToken(accessToken);
            responseDto.setRefreshToken(rawRefreshToken);
            return responseDto;

        }
    }

    // Helpers

    public RefreshTokenModel findUserActiveSessions(int userId, String deviceInfo) {
        List<RefreshTokenModel> userSessions = refreshTokenRepository.findByUserIdAndDeviceInfoAndRevokedFalse(userId, deviceInfo);
        if (userSessions.size() == 0) {
            return null;
        } else if (userSessions.size() == 1) {
            return userSessions.getFirst();

        } else {
            throw ApiException.unAuthenticated("User can not have more than one session against one device signature.");
        }
    }

    public String getOpaqueRefreshToken(HttpServletRequest request, int userId) {
        String deviceInfo = generateDeviceSignature(request);
        RefreshTokenModel activeSession = findUserActiveSessions(userId, deviceInfo);
        if (activeSession == null) {
            return createRefreshTokenEntity(userId, deviceInfo);
        } else {
            activeSession.setRevoked(true);
            refreshTokenRepository.save(activeSession);
            return createRefreshTokenEntity(userId, deviceInfo);
        }
    }

    private String detectPlatform(String userAgent) {
        userAgent = userAgent.toLowerCase();
        if (userAgent.contains("mobile") || userAgent.contains("android") || userAgent.contains("iphone")) {
            return "MOBILE";
        } else if (userAgent.contains("postman")) {
            return "POSTMAN";
        } else {
            return "WEB";
        }
    }

    public String generateDeviceSignature(HttpServletRequest request) {
        String platform = detectPlatform(request.getHeader("User-Agent"));
        String userId = request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : "ANONYMOUS";

        // Optional: include IP for extra security
        String ip = request.getRemoteAddr();

        // Combine stable parts
        String signatureSource = userId + "|" + platform + "|" + ip;

        // Hash it to create a concrete signature
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(signatureSource.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to generate device signature", e);
        }
    }

    public String createRefreshTokenEntity(int userId, String deviceInfo) {
        String refreshToken = JwtUtils.generateOpaqueRefreshToken();
        String hashedRefreshToken = bCryptPasswordEncoder.encode(refreshToken);
        RefreshTokenModel refreshTokenModel = new RefreshTokenModel();
        refreshTokenModel.setHash(hashedRefreshToken);
        refreshTokenModel.setUserId(userId);
        refreshTokenModel.setDeviceInfo(deviceInfo);
        refreshTokenModel.setExpiry(Instant.now().plus(7, ChronoUnit.DAYS));
        refreshTokenRepository.save(refreshTokenModel);
        return refreshToken;

    }

}