package com.example.VacationRequest.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    public String generateAccessToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername()) // email
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey())
                .compact();
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String email = extractEmail(token);
        return email.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // ✅ used by frontend for showing token expiry
    public long getAccessTokenTtlSeconds() {
        return jwtExpiration / 1000;
    }

    public Duration refreshDuration(boolean rememberMe) {
        return rememberMe ? Duration.ofDays(30) : Duration.ofDays(1);
    }
    public String generateRefreshToken(UserDetails userDetails, Duration ttl) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("typ", "refresh");

        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + ttl.toMillis()))
                .signWith(getSignInKey())
                .compact();
    }

    public boolean isRefreshTokenValid(String token, UserDetails userDetails) {
        Claims claims = extractAllClaims(token);
        String typ = claims.get("typ", String.class);
        return "refresh".equals(typ)
                && claims.getSubject().equals(userDetails.getUsername())
                && claims.getExpiration().after(new Date());
    }

}