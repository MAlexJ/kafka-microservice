package com.malex.subscriptionservice.kafka.producer;

import com.malex.subscriptionservice.model.dto.SubscriptionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaPublisherService {

  private final KafkaTemplate<String, Object> kafkaTemplate;

  @Value("${kafka.subscription.topic}")
  private String topic;

  public Mono<Void> send(SubscriptionDto subscription) {
    kafkaTemplate
        .send(topic, subscription.id(), subscription)
        .thenAccept(
            result -> {
              var metadata = result.getRecordMetadata();
              var offset = metadata.offset();
              var partition = metadata.partition();
              log.info(
                  "Message was sent, topic - {}, partition - {}, offset - {}",
                  metadata.topic(),
                  partition,
                  offset);
            });
    return Mono.empty();
  }
}
