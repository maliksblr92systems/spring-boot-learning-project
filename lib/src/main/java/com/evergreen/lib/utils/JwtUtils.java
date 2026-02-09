package com.evergreen.lib.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtUtils {

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String generateJwtToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", email);

        String userEmail = String.valueOf(email);
        return Jwts.builder().claims(claims).subject(userEmail).issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() * 60 * 60 * 30)) // 30
                // expiry
                // .and()
                .signWith(getKey()).compact();
    }
}
