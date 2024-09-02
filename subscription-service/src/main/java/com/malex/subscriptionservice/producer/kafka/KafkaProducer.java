package com.malex.subscriptionservice.producer.kafka;

import com.malex.subscriptionservice.model.SubscriptionEvent;
import com.malex.subscriptionservice.property.KafkaTopicProperties;
import com.malex.subscriptionservice.service.formatter.MessageFormatter;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {

  private final MessageFormatter formatter;
  private final KafkaTopicProperties topicProperties;

  private final ReactiveKafkaProducerTemplate<String, SubscriptionEvent> reactiveKafkaProducer;

  public Mono<RecordMetadata> sendEvent(SubscriptionEvent event) {
    var key = UUID.randomUUID().toString();
    var topic = topicProperties.getOut();
    return reactiveKafkaProducer
        .send(topic, key, event)
        .map(
            senderResult -> {
              Exception exception = senderResult.exception();
              if (exception != null) {
                log.warn("Exception occurred while sending message", exception);
              }

              var recordMetadata = senderResult.recordMetadata();
              log.info(
                  "Send event: key - {}, value - {}, topic - {}, partition - {} offset - {}",
                  key,
                  formatter.shortMessageInfo(event),
                  recordMetadata.topic(),
                  recordMetadata.partition(),
                  recordMetadata.offset());

              return recordMetadata;
            });
  }
}
