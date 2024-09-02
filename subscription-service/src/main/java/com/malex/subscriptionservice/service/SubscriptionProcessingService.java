package com.malex.subscriptionservice.service;

import com.malex.subscriptionservice.model.event.SubscriptionEvent;
import com.malex.subscriptionservice.producer.kafka.KafkaProducer;
import com.malex.subscriptionservice.service.storage.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionProcessingService {

  private final KafkaProducer publisher;
  private final SubscriptionService subscriptionService;

  public Flux<RecordMetadata> publishEvent(SubscriptionEvent event) {
    return subscriptionService
        .findSubscriptionsByCriteria(event.isActive())
        .flatMap(publisher::sendEvent);
  }
}
