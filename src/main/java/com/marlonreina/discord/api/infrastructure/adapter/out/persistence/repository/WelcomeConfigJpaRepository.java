package com.marlonreina.discord.api.infrastructure.adapter.out.persistence.repository;

import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.entity.WelcomeConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WelcomeConfigJpaRepository extends JpaRepository<WelcomeConfigEntity, String> {
}