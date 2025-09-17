package com.project.back_end.services;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Service
public class TokenService {

    /** Load from config or env (Base64 or plain text). Never hardcode keys in code. */
    @Value("${jwt.secret}")
    private String secret; // e.g., a 256-bit base64 string
    @Value("${jwt.ttlMillis:36000000}") // default 10 hours
    private long ttlMillis;

    /** Build a secure HMAC key from the configured secret. */
    private Key signingKey() {
        // Supports plain or Base64-encoded secret strings.
        try {
            // If secret is Base64, decode; else use raw bytes. Keys.hmacShaKeyFor checks size.
            byte[] bytes = io.jsonwebtoken.io.Decoders.BASE64.decode(secret);
            return Keys.hmacShaKeyFor(bytes);
        } catch (IllegalArgumentException notBase64) {
            return Keys.hmacShaKeyFor(secret.getBytes());
        }
    }

    /** Generate a JWT using the user's EMAIL as subject. You can pass extra claims if needed. */
    public String generateToken(String email, Map<String, Object> extraClaims) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setClaims(extraClaims == null ? Jwts.claims() : Jwts.claims(extraClaims))
                .setSubject(email)                         // <- email per rubric
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusMillis(ttlMillis)))
                .signWith(signingKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(String email) {
        return generateToken(email, null);
    }

    /** Validate token and ensure it belongs to this email. */
    public boolean validateToken(String token, String email) {
        try {
            final String subject = extractSubject(token);
            return email.equalsIgnoreCase(subject) && !isExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
            return false; // invalid signature, malformed, expired, etc.
        }
    }

    public boolean isExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public String extractSubject(String token) {
        return extractAllClaims(token).getSubject();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
