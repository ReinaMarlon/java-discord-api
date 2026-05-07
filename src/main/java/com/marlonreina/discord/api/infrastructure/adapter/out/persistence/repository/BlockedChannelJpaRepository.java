package com.marlonreina.discord.api.infrastructure.adapter.out.persistence.repository;

import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.entity.BlockedChannelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlockedChannelJpaRepository extends JpaRepository<BlockedChannelEntity, Long> {

    List<BlockedChannelEntity> findByGuildId(String guildId);
}
