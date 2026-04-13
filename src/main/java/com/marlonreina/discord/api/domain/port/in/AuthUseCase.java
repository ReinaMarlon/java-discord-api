package com.marlonreina.discord.api.domain.port.in;

import com.marlonreina.discord.api.shared.dto.AuthResponse;

public interface AuthUseCase {
    AuthResponse authenticate(String code);
}