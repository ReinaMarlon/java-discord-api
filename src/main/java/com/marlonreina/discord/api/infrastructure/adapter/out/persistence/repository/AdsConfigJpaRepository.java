package com.marlonreina.discord.api.infrastructure.adapter.out.persistence.repository;

import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.entity.AdsConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdsConfigJpaRepository extends JpaRepository<AdsConfigEntity, String> {
}
