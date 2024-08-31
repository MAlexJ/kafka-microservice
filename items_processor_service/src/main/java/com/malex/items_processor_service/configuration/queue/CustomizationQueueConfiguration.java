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
public class CustomizationQueueConfiguration {

  @Value("${custom.rabbitmq.queue.customization}")
  private String queue;

  @Value("${custom.rabbitmq.exchange.customization}")
  private String exchange;

  @Value("${custom.rabbitmq.routing.key.customization}")
  private String routingKey;

  @Bean
  public Queue customizationQueue() {
    return QueueBuilder.durable(queue).build();
  }

  @Bean
  public DirectExchange customizationExchange() {
    return ExchangeBuilder.directExchange(exchange).build();
  }

  @Bean
  public Binding customizationBinding(
      Queue customizationQueue, DirectExchange customizationExchange) {
    return BindingBuilder.bind(customizationQueue).to(customizationExchange).with(routingKey);
  }
}
