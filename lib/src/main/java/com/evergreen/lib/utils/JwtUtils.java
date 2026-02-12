package com.evergreen.lib.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import javax.crypto.SecretKey;

import com.evergreen.lib.dtos.appuser.AuthUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class JwtUtils {

    public static String extractUsername(String key, String token) {
        return extractClaim(key, token, Claims::getSubject);

    }

    public static long parseExpiryToMillis(String expiry) {

        long value = Long.parseLong(expiry.substring(0, expiry.length() - 1));
        char unit = expiry.charAt(expiry.length() - 1);

        return switch (unit) {
        case 's' -> value * 1000;
        case 'm' -> value * 60 * 1000;
        case 'h' -> value * 60 * 60 * 1000;
        case 'd' -> value * 24 * 60 * 60 * 1000;
        default -> throw new IllegalArgumentException("Invalid expiry format: " + expiry);
        };
    }

    public static AuthUser extractAuthUser(String key, String token) {
        SecretKey secretKey = getKey(key);
        Claims claims = Jwts.parser().verifyWith(secretKey).build().parseClaimsJws(token).getBody();
        Integer userId = Integer.valueOf(claims.getSubject());
        String email = claims.get("email", String.class);
        List<String> roles = claims.get("roles", List.class);
        return new AuthUser(userId, email, roles);

    }

    public static SecretKey getKey(String key) {
        byte[] keyBytes = Decoders.BASE64.decode(key);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    // public static <T> T extractClaim(String key, String token, Function<Claims, T> claimsResolver) {
    public static <T> T extractClaim(String key, String token, Function<Claims, T> claimsResolver) {

        final Claims claims = Jwts.parser().verifyWith(getKey(key)).build().parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }

    public static String generateAccessToken(String key, String expiry, AuthUser authUser) {
        String userIdAsString = String.valueOf(authUser.id());
        String email = authUser.email();
        List<String> roles = authUser.roles();

        long expiryMillis = parseExpiryToMillis(expiry);
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiryMillis);

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userIdAsString);
        claims.put("email", email);
        claims.put("roles", roles);

        return Jwts.builder().claims(claims).subject(userIdAsString).issuedAt(new Date(System.currentTimeMillis())).expiration(expiryDate).signWith(getKey(key)).compact();
    }

    public static String generateOpaqueRefreshToken() {
        return UUID.randomUUID().toString();
    }

    public static String generateRefreshToken(String key, String expiry, AuthUser authUser) {
        String userIdAsString = String.valueOf(authUser.id());
        String email = authUser.email();
        List<String> roles = authUser.roles();

        long expiryMillis = parseExpiryToMillis(expiry);
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiryMillis);

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userIdAsString);
        claims.put("email", email);
        claims.put("roles", roles);

        return Jwts.builder().claims(claims).subject(userIdAsString).issuedAt(new Date(System.currentTimeMillis())).expiration(expiryDate).signWith(getKey(key)).compact();
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
