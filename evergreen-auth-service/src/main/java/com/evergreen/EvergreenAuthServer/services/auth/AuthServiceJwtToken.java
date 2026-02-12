package com.evergreen.EvergreenAuthServer.services.auth;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
import com.evergreen.lib.dtos.appuser.AppUserDto;
import com.evergreen.lib.dtos.appuser.AuthUser;
import com.evergreen.lib.utils.ApiException;
import com.evergreen.lib.utils.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthServiceJwtToken implements IAuthService {

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

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(appUser.getId(), password));
        if (!authentication.isAuthenticated()) {
            throw ApiException.unAuthenticated("Not authenticated");
        }
        AuthUser authUser = new AuthUser(appUser.getId(), appUser.getEmail(), List.of());
        String accessToken = JwtUtils.generateAccessToken(ACCESS_TOKEN_SECRET, ACCESS_TOKEN_EXPIRY, authUser);

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
        AppUserModel alreadyExistsByEmail = appUserRepository.findByEmail(email);
        if (alreadyExistsByEmail != null) {
            throw ApiException.badRequest("User already exists with  email '" + email + "' .");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        AppUserModel newAppUser = new AppUserModel();
        newAppUser.setEmail(email);
        newAppUser.setPassword(encodedPassword);
        newAppUser = appUserRepository.save(newAppUser);

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(newAppUser.getId(), password));
        if (!authentication.isAuthenticated()) {
            throw ApiException.unAuthenticated("Not authenticated");
        }
        AuthUser authUser = new AuthUser(newAppUser.getId(), newAppUser.getEmail(), List.of());

        String accessToken = JwtUtils.generateAccessToken(ACCESS_TOKEN_SECRET, ACCESS_TOKEN_EXPIRY, authUser);
        return RegisterUserResponseDto.build(appUserMapper.toDto(newAppUser), accessToken);

    }

    public UserIsAuthenticatedResponseDto isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail userPrincipal = (CustomUserDetail) authentication.getPrincipal();
        AppUserModel appUser = appUserRepository.findByEmail(userPrincipal.getUsername());
        return new UserIsAuthenticatedResponseDto(appUserMapper.toDto(appUser));

    }

    // Helpers

    public String generateDeviceInfo(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        String ip = request.getRemoteAddr();
        String sessionId = request.getSession().getId();
        String deviceInfo = userAgent + "|" + ip + "|" + sessionId; // simple device signature
        return deviceInfo;
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

    public String getOpaqueRefreshToken(HttpServletRequest request, int userId) {
        String deviceInfo = generateDeviceInfo(request);
        List<RefreshTokenModel> previousTokens = refreshTokenRepository.findByUserIdAndDeviceInfoAndRevokedFalse(userId, deviceInfo);
        if (previousTokens.size() > 1) {
            throw ApiException.unAuthenticated("Logic fails at login");
        } else if (previousTokens.size() == 1) {
            RefreshTokenModel found = previousTokens.getFirst();
            if (found == null) {
                return createRefreshTokenEntity(userId, deviceInfo);
            } else {
                found.setRevoked(true);
                refreshTokenRepository.save(found);
                return createRefreshTokenEntity(userId, deviceInfo);
            }
        } else {
            return createRefreshTokenEntity(userId, deviceInfo);

        }
    }

    // Unuse implementations for JWT based refresh token
    // not recommende because
    // in case of db comporimise the refresh tokens can be exposed

    // Pros
    // 1-You can validate signature without DB
    // 2-User info inside token
    // 3-Easy implementation

    // Cons
    // 1-JWT is self-contained → if DB is leaked, attacker gets valid tokens immediately
    // 2-You are storing raw token (not hashed)
    // 3-Harder to implement reuse detection properly
    // 4-Bigger token size

    public String getJwtRefreshToken(AppUserDto appUser, HttpServletRequest request) {

        String userAgent = request.getHeader("User-Agent");
        String ip = request.getRemoteAddr();
        String sessionId = request.getSession().getId();
        String deviceInfo = userAgent + "|" + ip + "|" + sessionId; // simple device signature

        AuthUser authUser = new AuthUser(appUser.getId(), appUser.getEmail(), List.of());
        List<RefreshTokenModel> optionalSession = refreshTokenRepository.findByUserIdAndDeviceInfoAndRevokedFalse(authUser.id(), deviceInfo);
        // if previous session exists
        if (optionalSession.size() > 0) {
            RefreshTokenModel previousSession = optionalSession.getFirst();
            if (previousSession.getExpiry().isBefore(Instant.now())) {
                // previous session is expired
                // revoke it
                // create new
                previousSession.setRevoked(true);
                refreshTokenRepository.save(previousSession);
                String refreshToken = JwtUtils.generateRefreshToken(REFRESH_TOKEN_SECRET, REFRESH_TOKEN_EXPIRY, authUser);
                RefreshTokenModel refreshTokenModel = new RefreshTokenModel();
                refreshTokenModel.setUserId(authUser.id());
                refreshTokenModel.setExpiry(Instant.now().plus(1, ChronoUnit.DAYS));
                refreshTokenModel.setRevoked(false);
                refreshTokenModel.setHash(refreshToken);
                refreshTokenModel.setDeviceInfo(deviceInfo);
                refreshTokenRepository.save(refreshTokenModel);
                return refreshToken;
            } else {
                return previousSession.getHash();

            }
        } else {
            String refreshToken = JwtUtils.generateRefreshToken(REFRESH_TOKEN_SECRET, REFRESH_TOKEN_EXPIRY, authUser);
            RefreshTokenModel refreshTokenModel = new RefreshTokenModel();
            refreshTokenModel.setUserId(authUser.id());
            refreshTokenModel.setExpiry(Instant.now().plus(1, ChronoUnit.DAYS));
            refreshTokenModel.setRevoked(false);
            refreshTokenModel.setHash(refreshToken);
            refreshTokenModel.setDeviceInfo(deviceInfo);
            refreshTokenRepository.save(refreshTokenModel);
            return refreshToken;
        }

    }

    public RefreshTokenResponseDto refresh(String refreshToken) {
        Optional<RefreshTokenModel> optionalRefreshTokenEntity = refreshTokenRepository.findByHash(refreshToken);
        if (!optionalRefreshTokenEntity.isPresent()) {
            throw ApiException.unAuthenticated("Invalid refresh token.");
        } else {
            RefreshTokenModel refreshTokenEntity = optionalRefreshTokenEntity.get();
            AppUserModel appUser = appUserRepository.findById(refreshTokenEntity.getUserId()).orElseThrow(() -> {
                throw ApiException.unAuthenticated("User not found.");
            });
            if (refreshTokenEntity.getExpiry().isBefore(Instant.now())) {
                throw ApiException.unAuthenticated("Expired refresh token.");
            }
            AuthUser authUser = new AuthUser(appUser.getId(), appUser.getEmail(), List.of());
            String accessToken = JwtUtils.generateAccessToken(ACCESS_TOKEN_SECRET, ACCESS_TOKEN_EXPIRY, authUser);
            RefreshTokenResponseDto refreshTokenResponseDto = new RefreshTokenResponseDto();
            refreshTokenResponseDto.setAccessToken(accessToken);
            refreshTokenResponseDto.setRefreshToken(refreshToken);
            return refreshTokenResponseDto;

        }
    }

}
