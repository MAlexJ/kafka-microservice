package com.malex.templateresolverservice.kafka.producer;

import com.malex.templateresolverservice.model.event.Message;
import com.malex.templateresolverservice.property.KafkaTopicConfigurationProperties;
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
  private final ReactiveKafkaProducerTemplate<String, Message> reactiveKafkaProducer;

  public Mono<String> sendMessage(String md5Hash, Message message) {
    return reactiveKafkaProducer
        .send(topicProperty.getOut(), md5Hash, message)
        .doOnSuccess(
            senderResult -> {
              Exception exception = senderResult.exception();
              if (exception != null) {
                log.warn("Exception occurred while sending message", exception);
              }
              var recordMetadata = senderResult.recordMetadata();
              log.info(
                  "Sent RSS item with md5Hash - {} to topic - {}, partition - {},  offset - {}",
                  md5Hash,
                  recordMetadata.topic(),
                  recordMetadata.partition(),
                  recordMetadata.offset());
            })
        .map(SenderResult::recordMetadata)
        .map(RecordMetadata::topic);
  }
}
