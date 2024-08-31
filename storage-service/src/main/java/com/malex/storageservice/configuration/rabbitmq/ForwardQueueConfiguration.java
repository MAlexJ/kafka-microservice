package com.malex.storageservice.configuration.rabbitmq;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ForwardQueueConfiguration {

  private final AmqpAdmin amqpAdmin;

  @Value("${rabbitmq.queue.forward}")
  private String forwardQueue;

  @Value("${rabbitmq.exchange.forward}")
  private String forwardExchange;

  @Value("${rabbitmq.routing.key.forward}")
  private String forwardRoutingKey;

  @PostConstruct
  public void setupForwardQueue() {
    // 1. declare exchange
    var directExchange = ExchangeBuilder.directExchange(forwardExchange).build();
    amqpAdmin.declareExchange(directExchange);

    // 2. declare queue
    var durableQueue = QueueBuilder.durable(forwardQueue).build();
    amqpAdmin.declareQueue(durableQueue);

    // 3. binding exchange with queue
    var binding =
        BindingBuilder.bind(durableQueue).to(directExchange).with(forwardRoutingKey).noargs();
    amqpAdmin.declareBinding(binding);
  }
}
