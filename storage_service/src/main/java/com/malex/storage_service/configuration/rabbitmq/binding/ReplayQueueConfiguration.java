package com.malex.storage_service.configuration.rabbitmq.binding;

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
public class ReplayQueueConfiguration {

  private final AmqpAdmin amqpAdmin;

  @Value("${rabbitmq.queue.replay}")
  private String replayQueue;

  @Value("${rabbitmq.exchange.replay}")
  private String replayExchange;

  @Value("${rabbitmq.routing.key.replay}")
  private String replayRoutingKey;

  @PostConstruct
  public void setupReplayQueue() {
    // 1. declare exchange
    var directExchange = ExchangeBuilder.directExchange(replayExchange).build();
    amqpAdmin.declareExchange(directExchange);

    // 2. declare queue
    var durableQueue = QueueBuilder.durable(replayQueue).build();
    amqpAdmin.declareQueue(durableQueue);

    // 3. binding exchange with queue
    var binding =
        BindingBuilder.bind(durableQueue).to(directExchange).with(replayRoutingKey).noargs();
    amqpAdmin.declareBinding(binding);
  }

}
