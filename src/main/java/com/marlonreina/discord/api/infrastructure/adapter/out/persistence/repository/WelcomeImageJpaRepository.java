package com.marlonreina.discord.api.infrastructure.adapter.out.persistence.repository;

import com.marlonreina.discord.api.infrastructure.adapter.out.persistence.entity.WelcomeImageEntity;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class WelcomeImageJpaRepository {

    private static final String CREATE_TABLE_SQL = """
            CREATE TABLE IF NOT EXISTS welcome_images (
                guild_id VARCHAR(255) PRIMARY KEY,
                image_url VARCHAR(2048),
                image_name VARCHAR(255),
                image_data BYTEA,
                image_hash VARCHAR(255),
                mime_type VARCHAR(255),
                width INTEGER NOT NULL DEFAULT 0,
                height INTEGER NOT NULL DEFAULT 0,
                updated_at TIMESTAMP
            )
            """;
    private static final String ADD_IMAGE_NAME_COLUMN_SQL = """
            ALTER TABLE welcome_images
            ADD COLUMN IF NOT EXISTS image_name VARCHAR(255)
            """;
    private static final String ADD_IMAGE_DATA_COLUMN_SQL = """
            ALTER TABLE welcome_images
            ADD COLUMN IF NOT EXISTS image_data BYTEA
            """;
    private static final String FIND_BY_ID_SQL = """
            SELECT guild_id, image_url, image_name, image_data, image_hash, mime_type, width, height, updated_at
            FROM welcome_images
            WHERE guild_id = ?
            """;
    private static final String INSERT_SQL = """
            INSERT INTO welcome_images (
                guild_id, image_url, image_name, image_data, image_hash, mime_type, width, height, updated_at
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;
    private static final String UPDATE_SQL = """
            UPDATE welcome_images
            SET image_url = ?,
                image_name = ?,
                image_data = ?,
                image_hash = ?,
                mime_type = ?,
                width = ?,
                height = ?,
                updated_at = ?
            WHERE guild_id = ?
            """;
    private static final RowMapper<WelcomeImageEntity> ROW_MAPPER = (resultSet, rowNum) -> {
        WelcomeImageEntity image = new WelcomeImageEntity();
        image.setGuildId(resultSet.getString("guild_id"));
        image.setImageUrl(resultSet.getString("image_url"));
        image.setImageName(resultSet.getString("image_name"));
        image.setImageData(resultSet.getBytes("image_data"));
        image.setImageHash(resultSet.getString("image_hash"));
        image.setMimeType(resultSet.getString("mime_type"));
        image.setWidth(resultSet.getInt("width"));
        image.setHeight(resultSet.getInt("height"));
        image.setUpdatedAt(resultSet.getObject("updated_at", LocalDateTime.class));

        return image;
    };

    private final JdbcOperations jdbcTemplate;

    public WelcomeImageJpaRepository(
            @Qualifier("welcomeImageJdbcTemplate") JdbcOperations jdbcTemplate
    ) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void createTable() {
        jdbcTemplate.execute(CREATE_TABLE_SQL);
        jdbcTemplate.execute(ADD_IMAGE_NAME_COLUMN_SQL);
        jdbcTemplate.execute(ADD_IMAGE_DATA_COLUMN_SQL);
    }

    public Optional<WelcomeImageEntity> findById(String guildId) {
        return jdbcTemplate.query(FIND_BY_ID_SQL, ROW_MAPPER, guildId)
                .stream()
                .findFirst();
    }

    public WelcomeImageEntity save(WelcomeImageEntity image) {
        image.setUpdatedAt(LocalDateTime.now());

        int updatedRows = jdbcTemplate.update(
                UPDATE_SQL,
                image.getImageUrl(),
                image.getImageName(),
                image.getImageData(),
                image.getImageHash(),
                image.getMimeType(),
                image.getWidth(),
                image.getHeight(),
                image.getUpdatedAt(),
                image.getGuildId()
        );

        if (updatedRows == 0) {
            jdbcTemplate.update(
                    INSERT_SQL,
                    image.getGuildId(),
                    image.getImageUrl(),
                    image.getImageName(),
                    image.getImageData(),
                    image.getImageHash(),
                    image.getMimeType(),
                    image.getWidth(),
                    image.getHeight(),
                    image.getUpdatedAt()
            );
        }

        return image;
    }
}
