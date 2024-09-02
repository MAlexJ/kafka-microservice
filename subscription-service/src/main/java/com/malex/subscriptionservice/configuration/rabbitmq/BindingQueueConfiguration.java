package com.malex.subscriptionservice.configuration.rabbitmq;

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
public class BindingQueueConfiguration {

  private final AmqpAdmin amqpAdmin;

  @Value("${rabbitmq.queue}")
  private String queue;

  @Value("${rabbitmq.exchange}")
  private String exchange;

  @Value("${rabbitmq.routing.key}")
  private String routingKey;

  @PostConstruct
  public void setupQueue() {
    var directExchange = ExchangeBuilder.directExchange(exchange).build();
    amqpAdmin.declareExchange(directExchange);

    var durableQueue = QueueBuilder.durable(queue).build();
    amqpAdmin.declareQueue(durableQueue);

    var binding = BindingBuilder.bind(durableQueue).to(directExchange).with(routingKey).noargs();
    amqpAdmin.declareBinding(binding);
  }
}
