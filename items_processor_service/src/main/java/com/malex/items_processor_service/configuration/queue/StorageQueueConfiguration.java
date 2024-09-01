package com.malex.items_processor_service.configuration.queue;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageQueueConfiguration {

  @Value("${custom.rabbitmq.queue.storage}")
  private String queue;

  @Value("${custom.rabbitmq.exchange.storage}")
  private String exchange;

  @Value("${custom.rabbitmq.routing.key.storage}")
  private String routingKey;

  @Bean
  @Qualifier("rabbitmq.queue.storage")
  public Queue storageQueue() {
    return QueueBuilder.durable(queue).build();
  }

  @Bean
  @Qualifier("rabbitmq.exchange.storage")
  public DirectExchange storageExchange() {
    return ExchangeBuilder.directExchange(exchange).build();
  }

  @Bean
  @Qualifier("rabbitmq.binding.storage")
  public Binding storageBinding(
      @Qualifier("rabbitmq.queue.storage") Queue queue,
      @Qualifier("rabbitmq.exchange.storage") DirectExchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with(routingKey);
  }
}
