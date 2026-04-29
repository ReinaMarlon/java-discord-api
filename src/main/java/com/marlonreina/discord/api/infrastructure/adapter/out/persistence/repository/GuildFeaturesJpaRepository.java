package com.marlonreina.discord.api.infrastructure.adapter.out.persistence.repository;

import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.entity.GuildFeaturesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuildFeaturesJpaRepository extends JpaRepository<GuildFeaturesEntity, String> {
}