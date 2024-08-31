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
public class TemplateQueueConfiguration {

  @Value("${custom.rabbitmq.queue.template}")
  private String queue;

  @Value("${custom.rabbitmq.exchange.template}")
  private String exchange;

  @Value("${custom.rabbitmq.routing.key.template}")
  private String routingKey;

  @Bean
  @Qualifier("rabbitmq.queue.template")
  public Queue templateQueue() {
    return QueueBuilder.durable(queue).build();
  }

  @Bean
  @Qualifier("rabbitmq.exchange.template")
  public DirectExchange templateExchange() {
    return ExchangeBuilder.directExchange(exchange).build();
  }

  @Bean
  @Qualifier("rabbitmq.binding.template")
  public Binding templateBinding(
      @Qualifier("rabbitmq.queue.template") Queue queue,
      @Qualifier("rabbitmq.exchange.template") DirectExchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with(routingKey);
  }
}
