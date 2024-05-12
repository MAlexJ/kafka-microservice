package com.malex.rssreaderservice.kafka.consumer;

import com.malex.rssreaderservice.model.Subscription;
import com.malex.rssreaderservice.webservice.RssReaderWebService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

  private final RssReaderWebService rssReaderService;

  /** Setup <a href="https://habr.com/ru/articles/742786/">Simple producer and consumer</a> */
  @KafkaListener(
      topics = "${kafka.subscription.topic}",
      properties = {"spring.json.value.default.type=com.malex.rssreaderservice.model.Subscription"})
  public void processMessage(
      Subscription subscription,
      @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
      @Header(KafkaHeaders.RECEIVED_TOPIC) List<String> topics,
      @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
    errorHandler(
        subscription,
        () -> {
          rssReaderService
              .readRss(subscription)
              .doOnNext(rssItem -> log.info("Push item - {}", rssItem))
              .subscribe();

          log.info("topic:{}, partition:{}, offset: {}", topics, partitions, offsets);
        });
  }

  private void errorHandler(Subscription subscription, Runnable r) {
    try {
      r.run();
    } catch (Exception ex) {
      String errorMessage =
          String.format("Can't process message - [%s], error - %s", subscription, ex.getMessage());
      log.error(errorMessage);
    }
  }
}
