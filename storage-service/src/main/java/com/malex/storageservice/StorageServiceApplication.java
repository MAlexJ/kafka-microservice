package com.malex.storageservice;

import com.rabbitmq.client.Connection;
import jakarta.annotation.PreDestroy;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableR2dbcRepositories
@RequiredArgsConstructor
public class StorageServiceApplication {

  private final Mono<Connection> brokerConnection;

  // Make sure the connection before the program is finished
  @PreDestroy
  public void close() throws Exception {
    Objects.requireNonNull(brokerConnection.block(), "AMQ connection not exist").close();
  }

  public static void main(String[] args) {
    SpringApplication.run(StorageServiceApplication.class, args);
  }
}
