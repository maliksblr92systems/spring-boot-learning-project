package com.evergreen.EvergreenPaymentServer.filters;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.evergreen.lib.dtos.appuser.AuthUser;
import com.evergreen.lib.utils.ApiException;
import com.evergreen.lib.utils.JwtUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.accessToken.secretKey}")
    private String ACCESS_TOKEN_SECRET;

    private static final AntPathMatcher pathMatcher = new AntPathMatcher();

    private static final List<String> PUBLIC_PATHS = List.of("/api/v1/auth/login", "/api/v1/auth/register", "/api/v1/jobs/csv/customer", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html");

    private HandlerExceptionResolver handlerExceptionResolver;

    @Autowired
    public JwtAuthenticationFilter(HandlerExceptionResolver handlerExceptionResolver) {
        this.handlerExceptionResolver = handlerExceptionResolver;

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        final String path = request.getRequestURI();
        final boolean shouldSkip = PUBLIC_PATHS.stream().anyMatch(p -> pathMatcher.match(p, path));
        return shouldSkip;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = null;
        AuthUser authUser = null;
        try {

            final String authHeader = request.getHeader("Authorization");

            if (authHeader == null) {
                throw ApiException.unAuthenticated("Missing authroziation header.");
            }
            if (!authHeader.startsWith("Bearer")) {
                throw ApiException.unAuthenticated("Invalid access token provided.");
            }
            accessToken = authHeader.substring(7); // skips "Bearer "
            authUser = JwtUtils.extractAuthUser(ACCESS_TOKEN_SECRET, accessToken);
            if (authUser == null) {
                throw ApiException.unAuthenticated("Invalid access token provided.");
            }
            List<GrantedAuthority> authorities2 = authUser.roles().stream().map(r -> new SimpleGrantedAuthority(r)).collect(Collectors.toList());
            final UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(authUser, null, authorities2);
            SecurityContextHolder.getContext().setAuthentication(auth);

            filterChain.doFilter(request, response);
            // without handleExceptionResolver you wont be able to handle exceptions in
            // GlobalExceptionHandler
        } catch (

        Exception ex) {
            this.handlerExceptionResolver.resolveException(request, response, null, ex);
        }

    }

}
