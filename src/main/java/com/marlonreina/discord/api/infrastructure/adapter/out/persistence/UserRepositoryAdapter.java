package com.marlonreina.discord.api.infrastructure.adapter.out.persistence;

import com.marlonreina.discord.api.domain.model.User;
import com.marlonreina.discord.api.domain.port.out.UserRepositoryPort;
import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.entity.UserEntity;
import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.repository.UserJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserJpaRepository repository;

    public UserRepositoryAdapter(UserJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<User> findById(String discordId) {
        return repository.findById(discordId)
                .map(this::toDomain);
    }

    @Override
    public void save(User user) {

        UserEntity entity = repository.findById(user.getId())
                .orElse(new UserEntity());

        entity.setDiscordId(user.getId());
        entity.setUsername(user.getUsername());
        entity.setAvatar(user.getAvatar());
        entity.setEmail(user.getEmail());
        entity.setAccessToken(user.getAccessToken());
        entity.setRefreshToken(user.getRefreshToken());
        entity.setExpiresAt(user.getExpiresAt());

        repository.save(entity);
    }


    private User toDomain(UserEntity entity) {

        User user = new User(
                entity.getDiscordId(),
                entity.getUsername(),
                entity.getAvatar(),
                entity.getEmail()
        );

        user.setAccessToken(entity.getAccessToken());
        user.setRefreshToken(entity.getRefreshToken());
        user.setExpiresAt(entity.getExpiresAt());

        return user;
    }
}
