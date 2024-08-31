package com.malex.items_processor_service.configuration.queue;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PublisherQueueConfiguration {

  @Value("${custom.rabbitmq.queue.publisher}")
  private String queue;

  @Value("${custom.rabbitmq.exchange.publisher}")
  private String exchange;

  @Value("${custom.rabbitmq.routing.key.publisher}")
  private String routingKey;

  @Bean
  public Queue publisherQueue() {
    return QueueBuilder.durable(queue).build();
  }

  @Bean
  public DirectExchange publisherExchange() {
    return ExchangeBuilder.directExchange(exchange).build();
  }

  @Bean
  public Binding publisherBinding(Queue publisherQueue, DirectExchange publisherExchange) {
    return BindingBuilder.bind(publisherQueue).to(publisherExchange).with(routingKey);
  }
}
