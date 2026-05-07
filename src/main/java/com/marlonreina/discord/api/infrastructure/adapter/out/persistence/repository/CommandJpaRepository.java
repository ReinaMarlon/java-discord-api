package com.marlonreina.discord.api.infrastructure.adapter.out.persistence.repository;

import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.entity.CommandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandJpaRepository extends JpaRepository<CommandEntity, Long> {
}
