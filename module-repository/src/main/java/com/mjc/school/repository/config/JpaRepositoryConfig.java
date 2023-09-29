package com.mjc.school.repository.config;

import com.mjc.school.repository.impl.BaseRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(
        basePackages = "com.mjc.school.repository.*",
        repositoryBaseClass = BaseRepositoryImpl.class
)
public class JpaRepositoryConfig {
}
