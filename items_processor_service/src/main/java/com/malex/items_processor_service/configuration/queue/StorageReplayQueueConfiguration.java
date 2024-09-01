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
public class StorageReplayQueueConfiguration {

  @Value("${custom.rabbitmq.queue.storage_replay}")
  private String queue;

  @Value("${custom.rabbitmq.exchange.storage_replay}")
  private String exchange;

  @Value("${custom.rabbitmq.routing.key.storage_replay}")
  private String routingKey;

  @Bean
  @Qualifier("rabbitmq.queue.storage_replay")
  public Queue storageReplayQueue() {
    return QueueBuilder.durable(queue).build();
  }

  @Bean
  @Qualifier("rabbitmq.exchange.storage_replay")
  public DirectExchange storageReplayExchange() {
    return ExchangeBuilder.directExchange(exchange).build();
  }

  @Bean
  @Qualifier("rabbitmq.binding.storage_replay")
  public Binding storageReplayBinding(
      @Qualifier("rabbitmq.queue.storage_replay") Queue queue,
      @Qualifier("rabbitmq.exchange.storage_replay") DirectExchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with(routingKey);
  }
}
