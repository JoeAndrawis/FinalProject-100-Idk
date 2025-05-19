package com.example.courseservice.config;

import com.example.courseservice.model.Course;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.IndexResolver;
import org.springframework.data.mongodb.core.index.MongoPersistentEntityIndexResolver;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import jakarta.annotation.PostConstruct;

@Configuration
public class MongoConfig {
    private final MongoMappingContext mongoMappingContext;

    public MongoConfig(MongoMappingContext mongoMappingContext) {
        this.mongoMappingContext = mongoMappingContext;
    }

    @PostConstruct
    public void initIndexes() {
        IndexResolver resolver = new MongoPersistentEntityIndexResolver(mongoMappingContext);
        resolver.resolveIndexFor(Course.class);
    }
}