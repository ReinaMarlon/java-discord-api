package com.marlonreina.discord.api.infrastructure.adapter.out.persistence.repository;

import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.entity.LogConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogConfigJpaRepository extends JpaRepository<LogConfigEntity, String> {
}