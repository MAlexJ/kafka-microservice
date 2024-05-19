package com.malex.filteringservice.kafka.consumer;

import com.malex.filteringservice.model.event.RssItem;
import com.malex.filteringservice.service.LogicProcessor;
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

  private final ReactiveKafkaConsumerTemplate<String, RssItem> reactiveKafkaConsumer;

  //  private final KafkaProducer producerService;
  //  private final Md5HashService md5HashService;
  //  private final FilterCriteriaService criteriaService;
  //
  //  @EventListener(ApplicationStartedEvent.class)
  //  public Flux<RssItem> consumerEventListener() {
  //    return reactiveKafkaConsumer
  //        .receiveAutoAck()
  //        .doOnNext(
  //            consumerRecord ->
  //                log.info(
  //                    "Received event: key - {}, value - {}, topic - {}, partition - {} offset -
  // {}",
  //                    consumerRecord.key(),
  //                    consumerRecord.value().getClass().getSimpleName(),
  //                    consumerRecord.topic(),
  //                    consumerRecord.partition(),
  //                    consumerRecord.offset()))
  //        .map(ConsumerRecord::value)
  //        .filterWhen(md5HashService::isNotExistItemByMd5Hash)
  //        .filterWhen(criteriaService::applyFilteringCriteriaIncludedOrExcluded)
  //        .flatMap(producerService::sendMessage)
  //        .flatMap(md5HashService::saveItemMd5Hash)
  //        .doOnError(throwable -> log.error("Error - {}", throwable.getMessage()));
  //  }

  private final LogicProcessor logicProcessor;

  @EventListener(ApplicationStartedEvent.class)
  public Flux<RssItem> consumerEventListener() {
    var events = receiveEvent();
    return logicProcessor
        .process(events)
        .doOnError(throwable -> log.error("Error - {}", throwable.getMessage()));
  }

  private Flux<RssItem> receiveEvent() {
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
        .map(ConsumerRecord::value);
  }
}
