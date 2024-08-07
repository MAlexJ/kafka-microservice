package com.malex.subscriptionservice.kafka.producer;

import com.malex.subscriptionservice.model.SubscriptionEvent;
import com.malex.subscriptionservice.property.KafkaTopicProperties;
import com.malex.subscriptionservice.utils.MessageFormatUtils;
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

  private final KafkaTopicProperties topicProperties;

  private final ReactiveKafkaProducerTemplate<String, SubscriptionEvent> reactiveKafkaProducer;

  public Mono<SenderResult<Void>> sendEvent(SubscriptionEvent event) {
    var key = UUID.randomUUID().toString();
    var topic = topicProperties.getOut();
    return reactiveKafkaProducer
        .send(topic, key, event)
        .doOnSuccess(
            senderResult -> {
              Exception exception = senderResult.exception();
              if (exception != null) {
                log.warn("Exception occurred while sending message", exception);
              }
              var recordMetadata = senderResult.recordMetadata();
              log.info(
                  "Send event: key - {}, value - {}, topic - {}, partition - {} offset - {}",
                  key,
                  MessageFormatUtils.shortMessageInfo(event),
                  recordMetadata.topic(),
                  recordMetadata.partition(),
                  recordMetadata.offset());
            });
  }
}
