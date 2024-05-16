package com.malex.rssreaderservice.kafka.consumer;

import com.malex.rssreaderservice.kafka.producer.KafkaProducer;
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
public class KafkaConsumer {

  private final RssReaderWebService rssReaderService;
  private final KafkaProducer producerService;

  /** Setup <a href="https://habr.com/ru/articles/742786/">Simple producer and consumer</a> */
  @KafkaListener(
      topics = "${kafka.topic.in}",
      properties = {"spring.json.value.default.type=com.malex.rssreaderservice.model.Subscription"})
  public void processMessage(
      Subscription subscription,
      @Header(KafkaHeaders.RECEIVED_KEY) String key,
      @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
      @Header(KafkaHeaders.RECEIVED_TOPIC) List<String> topics,
      @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
    errorHandler(
        subscription,
        () -> {
          log.info(
              "Received message key:{} topic:{}, partition:{}, offset: {}",
              key,
              topics,
              partitions,
              offsets);
          rssReaderService.readRss(subscription).map(producerService::send).subscribe();
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
