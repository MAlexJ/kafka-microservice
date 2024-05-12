package com.malex.rssreaderservice.service.consumer;

import com.malex.rssreaderservice.model.SubscriptionDto;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumerService {

  /** Setup <a href="https://habr.com/ru/articles/742786/">Simple producer and consumer</a> */
  @KafkaListener(
      topics = "${kafka.subscription.topic}",
      properties = {
        "spring.json.value.default.type=com.malex.rssreaderservice.model.SubscriptionDto"
      })
  public void processMessage(
      SubscriptionDto subscription, //
      @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions, //
      @Header(KafkaHeaders.RECEIVED_TOPIC) List<String> topics, //
      @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
    errorHandler(
        subscription,
        () ->
            log.info(
                "topic:{}, partition:{}, offset: {}, message: {}",
                topics,
                partitions,
                offsets,
                subscription));
  }

  private void errorHandler(SubscriptionDto subscription, Runnable r) {
    try {
      r.run();
    } catch (Exception ex) {
      String errorMessage =
          String.format("Can't process message - [%s], error - %s", subscription, ex.getMessage());
      log.error(errorMessage);
    }
  }
}
