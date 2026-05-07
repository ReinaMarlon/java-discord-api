package com.marlonreina.discord.api.infrastructure.adapter.out.persistence.repository;

import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.entity.WelcomeImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WelcomeImageJpaRepository extends JpaRepository<WelcomeImageEntity, String> {
}
