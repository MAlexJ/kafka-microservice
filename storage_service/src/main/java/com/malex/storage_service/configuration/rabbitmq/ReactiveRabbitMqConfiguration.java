package com.malex.storage_service.configuration.rabbitmq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Delivery;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.RabbitFlux;
import reactor.rabbitmq.Receiver;
import reactor.rabbitmq.ReceiverOptions;
import reactor.rabbitmq.Sender;
import reactor.rabbitmq.SenderOptions;

@Configuration
public class ReactiveRabbitMqConfiguration {

  private static final String CONNECTION_NAME = "storage-reactor-rabbitmq";

  // the mono for connection, it is cached to re-use the connection across sender
  // and receiver instances this should work properly in most cases
  @Bean
  public Mono<Connection> connectionMono(RabbitProperties rabbitProperties) {
    ConnectionFactory connectionFactory = new ConnectionFactory();
    connectionFactory.setHost(rabbitProperties.getHost());
    connectionFactory.setPort(rabbitProperties.getPort());
    connectionFactory.setUsername(rabbitProperties.getUsername());
    connectionFactory.setPassword(rabbitProperties.getPassword());
    connectionFactory.setVirtualHost(rabbitProperties.getVirtualHost());
    return Mono.fromCallable(() -> connectionFactory.newConnection(CONNECTION_NAME)).cache();
  }

  @Bean
  public Sender sender(Mono<Connection> connectionMono) {
    return RabbitFlux.createSender(new SenderOptions().connectionMono(connectionMono));
  }

  @Bean
  public Receiver receiver(Mono<Connection> connectionMono) {
    return RabbitFlux.createReceiver(new ReceiverOptions().connectionMono(connectionMono));
  }

  @Value("${rabbitmq.queue.forward}")
  private String forwardQueue;

  @Bean
  public Flux<Delivery> deliveryForwardFlux(Receiver receiver) {
    return receiver.consumeAutoAck(forwardQueue);
  }
}
