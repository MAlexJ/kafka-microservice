package com.malex.subscriptionservice.consumer.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.malex.subscriptionservice.exception.SubscriptionEventException;
import com.malex.subscriptionservice.model.event.SubscriptionEvent;
import com.malex.subscriptionservice.service.SubscriptionProcessingService;
import com.rabbitmq.client.Delivery;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.Receiver;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMqConsumer {

  private final Receiver receiver;

  private final ObjectMapper jsonMapper;

  private final SubscriptionProcessingService service;

  @Value("${rabbitmq.queue}")
  private String queue;

  @Bean
  public Disposable handleSubscriptionEvent() {
    return receiver
        .consumeAutoAck(queue)
        .doOnError(e -> log.error("Receive failed", e))
        .flatMap(this::processingEvent)
        .flatMap(service::publishEvent)
        .subscribe();
  }

  @SneakyThrows
  private Mono<SubscriptionEvent> processingEvent(Delivery delivery) {
    return Mono.fromSupplier(delivery::getBody)
        .map(body -> new String(body, StandardCharsets.UTF_8))
        .handle(
            (json, sink) -> {
              try {
                sink.next(jsonMapper.readValue(json, SubscriptionEvent.class));
              } catch (JsonProcessingException e) {
                sink.error(new SubscriptionEventException(e));
              }
            });
  }
}
