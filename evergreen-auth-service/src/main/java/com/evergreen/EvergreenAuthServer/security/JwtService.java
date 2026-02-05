package com.evergreen.EvergreenAuthServer.security;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

import com.evergreen.EvergreenAuthServer.models.AppUserModel;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {

    private final static String SECRET_KEY = "ksby871FBLS3ksby871ksby871FBLS3ksby871ksby871FBLS3ksby871";

    public SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateJwtToken(AppUserModel appUser) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", appUser.getEmail());

        String userEmail = String.valueOf(appUser.getEmail());
        return Jwts.builder().claims(claims).subject(userEmail).issuedAt(new Date(System.currentTimeMillis())).expiration(new Date(System.currentTimeMillis() * 60 * 60 * 30)) // 30 minutes
                                                                                                                                                                               // expiry
                // .and()
                .signWith(getKey()).compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser().verifyWith(getKey()).build().parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }
}
