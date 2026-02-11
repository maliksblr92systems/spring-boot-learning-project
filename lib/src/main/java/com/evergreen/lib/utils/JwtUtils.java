package com.evergreen.lib.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import com.evergreen.lib.dtos.appuser.AuthUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class JwtUtils {

    public static String extractUsername(String key, String accesstoken) {
        return extractClaim(key, accesstoken, Claims::getSubject);

    }

    public static AuthUser extractAuthUser(String key, String accesstoken) {
        SecretKey secretKey = getKey(key);
        Claims claims = Jwts.parser().verifyWith(getKey(key)).build().parseClaimsJws(accesstoken).getBody();
        Integer userId = Integer.valueOf(claims.getSubject());
        String email = claims.get("email", String.class);
        List<String> roles = claims.get("roles", List.class);
        return new AuthUser(userId, email, roles);

    }

    public static SecretKey getKey(String key) {
        byte[] keyBytes = Decoders.BASE64.decode(key);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    // public static <T> T extractClaim(String key, String accesstoken, Function<Claims, T> claimsResolver) {
    public static <T> T extractClaim(String key, String accesstoken, Function<Claims, T> claimsResolver) {

        final Claims claims = Jwts.parser().verifyWith(getKey(key)).build().parseClaimsJws(accesstoken).getBody();
        return claimsResolver.apply(claims);
    }

    public static String generateJwtToken2(String key, AuthUser authUser) {
        String userIdAsString = String.valueOf(authUser.id());
        String email = authUser.email();
        List<String> roles = authUser.roles();

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userIdAsString);
        claims.put("email", email);
        claims.put("roles", roles);

        return Jwts.builder().claims(claims).subject(userIdAsString).issuedAt(new Date(System.currentTimeMillis())).expiration(new Date(System.currentTimeMillis() * 60 * 60 * 30)) // 30
                // expiry
                // .and()
                .signWith(getKey(key)).compact();
    }

    public static String generateJwtToken(String key, int id) {
        String userIdAsString = String.valueOf(id);

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userIdAsString);
        return Jwts.builder().claims(claims).subject(userIdAsString).issuedAt(new Date(System.currentTimeMillis())).expiration(new Date(System.currentTimeMillis() * 60 * 60 * 30)) // 30
                // expiry
                // .and()
                .signWith(getKey(key)).compact();
    }

}
