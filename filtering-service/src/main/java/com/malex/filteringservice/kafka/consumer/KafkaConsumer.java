package com.malex.filteringservice.kafka.consumer;

import static com.malex.filteringservice.utils.MessageFormatUtils.shortMessageInfo;

import com.malex.filteringservice.model.event.ItemEvent;
import com.malex.filteringservice.service.KafkaEventProcessor;
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

  private final ReactiveKafkaConsumerTemplate<String, ItemEvent> reactiveKafkaConsumer;

  private final KafkaEventProcessor eventProcessor;

  @EventListener(ApplicationStartedEvent.class)
  public Flux<ItemEvent> consumerEventListener() {

    // Note: test it!
    //    Flux<ItemEvent> itemEventFlux =
    //        handleIncomingEvent()
    //            .transform(itemIventFlux -> eventProcessor.processing(itemIventFlux))
    //            .doOnError(throwable -> log.error("Error - {}", throwable.getMessage()));

    return eventProcessor
        .processing(handleIncomingEvent())
        .doOnError(throwable -> log.error("Error - {}", throwable.getMessage()));
  }

  private Flux<ItemEvent> handleIncomingEvent() {
    return reactiveKafkaConsumer
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
        .map(ConsumerRecord::value);
  }
}
