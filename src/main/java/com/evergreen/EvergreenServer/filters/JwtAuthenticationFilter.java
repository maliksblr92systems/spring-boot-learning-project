package com.evergreen.EvergreenServer.filters;

import java.io.IOException;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.evergreen.EvergreenServer.advices.ApiException;
import com.evergreen.EvergreenServer.implementations.AppUserDetailsService;
import com.evergreen.EvergreenServer.security.JwtService;
import com.evergreen.EvergreenServer.security.dtos.CustomUserDetail;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final AppUserDetailsService appUserDetailsService;
    private static final List<String> PUBLIC_PATHS = List.of("/api/auth/login", "/api/auth/register", "/api/jobs/csv/customer");

    JwtAuthenticationFilter(JwtService jwtService, AppUserDetailsService appUserDetailsService) {
        this.jwtService = jwtService;
        this.appUserDetailsService = appUserDetailsService;

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();

        return PUBLIC_PATHS.stream().anyMatch(path::equals);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == "") {
            throw ApiException.unAuthenticated("Missing access token.");
        }
        String[] authHeaderArr = authHeader.split(" ");
        String accessToken = authHeaderArr[1];
        if (accessToken == "") {

            throw ApiException.unAuthenticated("Missing access token.");
        }
        String userEmail = jwtService.extractUsername(accessToken);
        CustomUserDetail userPrincipal = appUserDetailsService.loadUserByUsername(userEmail);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);



        filterChain.doFilter(request, response);


        // throw new UnsupportedOperationException("Unimplemented method 'doFilterInternal'");
    }

}
