package com.malex.subscriptionservice.kafka.producer;

import com.malex.subscriptionservice.model.Subscription;
import com.malex.subscriptionservice.property.KafkaTopicProperty;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.SenderResult;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {

  private final KafkaTopicProperty topicProperty;

  private final ReactiveKafkaProducerTemplate<String, Subscription> reactiveKafkaProducer;

  public Mono<SenderResult<Void>> sendMessage(Subscription message) {
    return reactiveKafkaProducer
        .send(topicProperty.getIn(), UUID.randomUUID().toString(), message)
        .doOnSuccess(
            senderResult -> {
              Exception exception = senderResult.exception();
              if (exception != null) {
                log.warn("Exception occurred while sending message", exception);
              }
              var recordMetadata = senderResult.recordMetadata();
              log.info(
                  "sent {}, topic {}, partition {},  offset : {}",
                  message,
                  recordMetadata.topic(),
                  recordMetadata.partition(),
                  recordMetadata.offset());
            });
  }
}
