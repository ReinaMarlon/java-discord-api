package com.marlonreina.discord.api.infrastructure.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class WelcomeImageDataSourceConfig {

    @Primary
    @Bean(name = "dataSource")
    public DataSource dataSource(Environment environment) {
        return hikariDataSource(
                property(environment, "spring.datasource.url"),
                property(environment, "spring.datasource.username"),
                property(environment, "spring.datasource.password"),
                property(environment, "spring.datasource.driver-class-name")
        );
    }

    @Bean(name = "welcomeImageDataSource")
    public DataSource welcomeImageDataSource(Environment environment) {
        return hikariDataSource(
                property(environment, "spring.datasource.welcome-image-url", "spring.datasource.url"),
                property(
                        environment,
                        "spring.datasource.welcome-image-username",
                        "spring.datasource.username"
                ),
                property(
                        environment,
                        "spring.datasource.welcome-image-password",
                        "spring.datasource.password"
                ),
                property(
                        environment,
                        "spring.datasource.welcome-image-driver-class-name",
                        "spring.datasource.driver-class-name"
                )
        );
    }

    private HikariDataSource hikariDataSource(
            String jdbcUrl,
            String username,
            String password,
            String driverClassName
    ) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        if (!driverClassName.isBlank()) {
            dataSource.setDriverClassName(driverClassName);
        }

        return dataSource;
    }

    @Bean(name = "welcomeImageJdbcTemplate")
    public JdbcTemplate welcomeImageJdbcTemplate(
            @Qualifier("welcomeImageDataSource") DataSource dataSource
    ) {
        return new JdbcTemplate(dataSource);
    }

    private String property(Environment environment, String key) {
        return environment.getProperty(key, "");
    }

    private String property(Environment environment, String key, String fallbackKey) {
        String value = environment.getProperty(key);
        if (value != null && !value.isBlank()) {
            return value;
        }

        return environment.getProperty(fallbackKey, "");
    }
}
