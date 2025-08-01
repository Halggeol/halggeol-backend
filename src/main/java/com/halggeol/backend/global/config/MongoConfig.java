package com.halggeol.backend.global.config;


import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackageClasses = {com.halggeol.backend.logs.repository.UserActionLogRepository.class})
public class MongoConfig {

    @Value("${spring.data.mongodb.uri}")
    private String MONGO_URI; // MongoDB URI from application properties


    @Bean
    public MongoClient mongoClient() {
        // Create and return a MongoClient instance
        return com.mongodb.client.MongoClients.create(MONGO_URI);
    }

    @Bean
    public MongoDatabaseFactory mongoDbFactory() {
        // Create and return a MongoDatabaseFactory instance
        return new SimpleMongoClientDatabaseFactory(mongoClient(), "user_log_db");
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        // Create and return a MongoTemplate instance
        return new MongoTemplate(mongoDbFactory());
    }
}
