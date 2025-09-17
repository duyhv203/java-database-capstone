package com.project.back_end.services;

import org.springframework.stereotype.Service;

@Service
public class TokenService {

    // Token validation logic (e.g., JWT verification) would go here
    public boolean isValidToken(String token) {
        // Dummy implementation
        return token != null && token.length() > 10;
    }

    public String generateToken(String username) {
        // Dummy token generation
        return "token_" + username + "_" + System.currentTimeMillis();
    }
}
