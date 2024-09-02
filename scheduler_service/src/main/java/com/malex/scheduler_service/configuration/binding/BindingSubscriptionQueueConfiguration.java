package com.malex.scheduler_service.configuration.binding;

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
public class BindingSubscriptionQueueConfiguration {

  @Value("${custom.rabbitmq.queue.subscription}")
  private String queue;

  @Value("${custom.rabbitmq.exchange.subscription}")
  private String exchange;

  @Value("${custom.rabbitmq.routing.key.subscription}")
  private String routingKey;

  @Bean
  public Queue subscriptionQueue() {
    return QueueBuilder.durable(queue).build();
  }

  @Bean
  public DirectExchange subscriptionExchange() {
    return ExchangeBuilder.directExchange(exchange).build();
  }

  @Bean
  public Binding subscriptionBinding(
      Queue customizationQueue, DirectExchange customizationExchange) {
    return BindingBuilder.bind(customizationQueue).to(customizationExchange).with(routingKey);
  }
}
