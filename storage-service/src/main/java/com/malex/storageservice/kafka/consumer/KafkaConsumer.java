package com.malex.storageservice.kafka.consumer;

import static com.malex.storageservice.utils.MessageFormatUtils.shortMessageInfo;

import com.malex.storageservice.model.event.ItemEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {

  private final ReactiveKafkaConsumerTemplate<String, ItemEvent> reactiveKafkaConsumer;

  @EventListener(ApplicationStartedEvent.class)
  public void consumerEventListener() {
    reactiveKafkaConsumer
        .receiveAutoAck()
        .doOnNext(
            consumerRecord ->
                log.info(
                    "Received event: key - {}, value - {}, topic - {}, partition - {} offset - {}",
                    consumerRecord.key(),
                    shortMessageInfo(consumerRecord.value()),
                    consumerRecord.topic(),
                    consumerRecord.partition(),
                    consumerRecord.offset()))
        .subscribe();
  }
}
