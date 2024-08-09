package com.malex.rssreaderservice.kafka.producer;

import com.malex.rssreaderservice.model.event.RssItemEvent;
import com.malex.rssreaderservice.property.KafkaTopicConfigurationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.SenderResult;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {

  private final KafkaTopicConfigurationProperties topicProperty;
  private final ReactiveKafkaProducerTemplate<String, RssItemEvent> reactiveKafkaProducer;

  public Mono<String> sendMessage(RssItemEvent item) {
    return reactiveKafkaProducer
        .send(topicProperty.getOut(), item.md5Hash(), item)
        .doOnSuccess(
            senderResult -> {
              Exception exception = senderResult.exception();
              if (exception != null) {
                log.warn("Exception occurred while sending message", exception);
              }
              var recordMetadata = senderResult.recordMetadata();
              log.info(
                  "Send event: key - {}, value - {}, topic - {}, partition - {} offset - {}",
                  item.md5Hash(),
                  item.getClass().getSimpleName(),
                  recordMetadata.topic(),
                  recordMetadata.partition(),
                  recordMetadata.offset());
            })
        .map(SenderResult::recordMetadata)
        .map(RecordMetadata::topic);
  }
}
