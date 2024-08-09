package com.malex.filteringservice.kafka.producer;

import static com.malex.filteringservice.utils.MessageFormatUtils.shortMessageInfo;

import com.malex.filteringservice.model.event.ItemEvent;
import com.malex.filteringservice.property.KafkaTopicConfigurationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {

  private final KafkaTopicConfigurationProperties topicProperty;
  private final ReactiveKafkaProducerTemplate<String, ItemEvent> reactiveKafkaProducer;

  public Mono<ItemEvent> sendKafkaEvent(ItemEvent event) {
    var topic = topicProperty.getOut();
    var key = event.md5Hash();
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
                  event.md5Hash(),
                  shortMessageInfo(event),
                  recordMetadata.topic(),
                  recordMetadata.partition(),
                  recordMetadata.offset());
            })
        .map(voidSenderResult -> event);
  }
}
