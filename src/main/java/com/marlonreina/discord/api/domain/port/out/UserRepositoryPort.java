package com.marlonreina.discord.api.domain.port.out;

import com.marlonreina.discord.api.domain.model.User;

import java.util.Optional;

public interface UserRepositoryPort {
    void save(User user);

    Optional<User> findById(String discordId);
}