package com.daniel.etl_teste.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(
    basePackages = "com.daniel.etl_teste.model.repository",
    mongoTemplateRef = "mainMongoTemplate"
)
public class MongoRepositoryConfig {
}
