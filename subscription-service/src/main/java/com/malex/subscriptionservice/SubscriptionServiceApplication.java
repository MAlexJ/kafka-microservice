package com.malex.subscriptionservice;

import com.rabbitmq.client.Connection;
import jakarta.annotation.PreDestroy;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RequiredArgsConstructor
public class SubscriptionServiceApplication {

  private final Mono<Connection> brokerConnection;

  /*
   * Close RabbitMQ connection
   */
  @PreDestroy
  public void close() throws Exception {
    Objects.requireNonNull(brokerConnection.block(), "AMQ connection not exist").close();
  }

  public static void main(String[] args) {
    SpringApplication.run(SubscriptionServiceApplication.class, args);
  }
}
