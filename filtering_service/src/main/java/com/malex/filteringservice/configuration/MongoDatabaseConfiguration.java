package com.malex.filteringservice.configuration;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.ReactiveMongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;

/**
 * Configuration mongo db:
 *
 * <ul>
 *   <li><a href="https://docs.spring.io/spring-data/mongodb/reference/index.html">Spring Data
 *       MongoDB</a>
 *   <li><a
 *       href="https://docs.spring.io/spring-data/mongodb/docs/2.0.0.RC2/reference/html/#mongo.reactive.repositories">Reactive
 *       MongoDB repositories</a>
 *   <li>
 * </ul>
 */
@Slf4j
@Configuration
@EnableReactiveMongoAuditing
@RequiredArgsConstructor
public class MongoDatabaseConfiguration extends AbstractReactiveMongoConfiguration {

  private final MongoProperties mongoProperties;

  @Bean
  public ReactiveMongoTransactionManager transactionManager(
      ReactiveMongoDatabaseFactory dbFactory) {
    log.info("MongoDb configuration reactive transaction manager");
    return new ReactiveMongoTransactionManager(dbFactory);
  }

  @Override
  protected String getDatabaseName() {
    var database = Objects.requireNonNull(mongoProperties.getDatabase());
    log.info("MongoDb configuration database properties: {}", database);
    return database;
  }

  @Override
  public MongoClient reactiveMongoClient() {
    var uri = Objects.requireNonNull(mongoProperties.getUri());
    log.info("MongoDb configuration URI property: {}", uri);
    return MongoClients.create(uri);
  }
}
