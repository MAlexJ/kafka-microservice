package com.malex.filteringservice.kafka.consumer;

import com.malex.filteringservice.kafka.producer.KafkaProducer;
import com.malex.filteringservice.model.event.RssItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {

  private final KafkaProducer producerService;
  private final ReactiveKafkaConsumerTemplate<String, RssItem> reactiveKafkaConsumer;

  @EventListener(ApplicationStartedEvent.class)
  public Flux<String> consumerEventListener() {
    return reactiveKafkaConsumer
        .receiveAutoAck()
        .doOnNext(
            consumerRecord ->
                log.info(
                    "Received event key - {}, value - {} from topic - {}, partition - {} offset - {}",
                    consumerRecord.key(),
                    consumerRecord.value().getClass().getSimpleName(),
                    consumerRecord.topic(),
                    consumerRecord.partition(),
                    consumerRecord.offset()))
        .map(ConsumerRecord::value)
        .flatMap(producerService::sendMessage)
        .doOnError(throwable -> log.error("Error - {}", throwable.getMessage()));
  }
}
