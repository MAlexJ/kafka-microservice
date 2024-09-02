package com.malex.subscriptionservice;

import com.malex.subscriptionservice.configuration.kafka.ReactiveKafkaConfiguration;
import com.malex.subscriptionservice.configuration.rabbitmq.BindingQueueConfiguration;
import com.malex.subscriptionservice.configuration.rabbitmq.ReactiveRabbitMqConfiguration;
import com.malex.subscriptionservice.consumer.rabbitmq.RabbitMqConsumer;
import com.malex.subscriptionservice.producer.kafka.KafkaProducer;
import com.rabbitmq.client.Connection;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;

@SpringBootTest
class SubscriptionServiceApplicationTests {

  @MockBean private KafkaProducer kafkaProducer;

  @MockBean private RabbitMqConsumer rabbitMqConsumer;

  @MockBean private Mono<Connection> brokerConnection;

  @MockBean private ReactiveKafkaConfiguration reactiveKafkaConfiguration;

  @MockBean private ReactiveRabbitMqConfiguration reactiveRabbitMqConfiguration;

  @MockBean private BindingQueueConfiguration queueBindingRabbitMqConfiguration;

  @Test
  void contextLoads() {}
}
