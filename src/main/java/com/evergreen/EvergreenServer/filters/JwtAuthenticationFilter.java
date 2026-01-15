package com.evergreen.EvergreenServer.filters;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import com.evergreen.EvergreenServer.advices.ApiException;
import com.evergreen.EvergreenServer.implementations.AppUserDetailsService;
import com.evergreen.EvergreenServer.security.JwtService;
import com.evergreen.EvergreenServer.security.dtos.CustomUserDetail;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final AntPathMatcher pathMatcher = new AntPathMatcher();

    private static final List<String> PUBLIC_PATHS =
            List.of("/api/auth/login", "/api/auth/register", "/api/jobs/csv/customer", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html");

    private HandlerExceptionResolver handlerExceptionResolver;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AppUserDetailsService appUserDetailsService;



    @Autowired
    public JwtAuthenticationFilter(HandlerExceptionResolver handlerExceptionResolver) {
        this.handlerExceptionResolver = handlerExceptionResolver;

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        boolean shouldSkip = PUBLIC_PATHS.stream().anyMatch(p -> pathMatcher.match(p, path));
        return shouldSkip;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String accessToken = null;
        String userEmail = null;
        try {
            String authHeader = request.getHeader("Authorization");


            if (authHeader == null) {
                throw ApiException.unAuthenticated("Missing authroziation header.");
            }
            if (!authHeader.startsWith("Bearer")) {
                throw ApiException.unAuthenticated("Invalid access token provided.");
            }
            accessToken = authHeader.substring(7); // skips "Bearer "

            userEmail = jwtService.extractUsername(accessToken);
            if (userEmail == null || userEmail.isBlank()) {
                throw ApiException.unAuthenticated("Invalid access token provided.");

            }


            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                CustomUserDetail userPrincipal = appUserDetailsService.loadUserByUsername(userEmail);
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

            filterChain.doFilter(request, response);
            // without handleExceptionResolver you wont be able to handle exceptions in GlobalExceptionHandler
        } catch (Exception ex) {
            this.handlerExceptionResolver.resolveException(request, response, null, ex);
        }


    }

}
