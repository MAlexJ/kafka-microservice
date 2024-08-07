package com.malex.storageservice.kafka.consumer;

import static com.malex.storageservice.utils.MessageFormatUtils.shortMessageInfo;

import com.malex.storageservice.model.entity.ItemEntity;
import com.malex.storageservice.model.event.ItemEvent;
import com.malex.storageservice.service.ItemService;
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

  private final ItemService service;

  private final ReactiveKafkaConsumerTemplate<String, ItemEvent> reactiveKafkaConsumer;

  @EventListener(ApplicationStartedEvent.class)
  public Flux<ItemEntity> consumerEventListener() {
    return reactiveKafkaConsumer
        .receiveAutoAck()
        .doOnNext(
            record ->
                log.debug(
                    "Received event: key - {}, value - {}, topic - {}, partition - {} offset - {}",
                    record.key(),
                    shortMessageInfo(record.value()),
                    record.topic(),
                    record.partition(),
                    record.offset()))
        .map(ConsumerRecord::value)
        .flatMap(service::save)
        .doOnError((e) -> log.error("Error processing Item event - {}", e.getMessage()));
  }
}
