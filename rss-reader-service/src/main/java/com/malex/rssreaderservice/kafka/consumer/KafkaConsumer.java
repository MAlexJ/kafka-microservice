package com.malex.rssreaderservice.kafka.consumer;

import com.malex.rssreaderservice.kafka.producer.KafkaProducer;
import com.malex.rssreaderservice.model.event.SubscriptionEvent;
import com.malex.rssreaderservice.webservice.RssReaderWebService;
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
  private final RssReaderWebService rssReaderService;
  private final ReactiveKafkaConsumerTemplate<String, SubscriptionEvent> reactiveKafkaConsumer;

  @EventListener(ApplicationStartedEvent.class)
  public Flux<String> consumerEventListener() {
    return reactiveKafkaConsumer
        .receiveAutoAck()
        .doOnNext(
            consumerRecord ->
                log.info(
                    "Received event: key - {}, value - {}, topic - {}, partition - {} offset - {}",
                    consumerRecord.key(),
                    consumerRecord.value().getClass().getSimpleName(),
                    consumerRecord.topic(),
                    consumerRecord.partition(),
                    consumerRecord.offset()))
        .map(ConsumerRecord::value)
        .flatMap(rssReaderService::readRss)
        .flatMap(producerService::sendMessage)
        .doOnError(throwable -> log.error("Error - {}", throwable.getMessage()));
  }
}
