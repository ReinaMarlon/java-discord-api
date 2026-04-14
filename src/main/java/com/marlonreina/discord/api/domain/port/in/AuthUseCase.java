package com.marlonreina.discord.api.domain.port.in;

public interface AuthUseCase {
    String authenticate(String code);
}