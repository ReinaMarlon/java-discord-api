package com.marlonreina.discord.api.domain.port.out;

import com.marlonreina.discord.api.domain.model.User;

public interface JwtPort {
    String generateToken(User user);

    String extractUserId(String token);
}