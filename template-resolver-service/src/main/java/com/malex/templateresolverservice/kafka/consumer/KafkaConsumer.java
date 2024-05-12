package com.malex.templateresolverservice.kafka.consumer;

import com.malex.templateresolverservice.model.RssItem;
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

  @KafkaListener(
      topics = "${kafka.topic.in}",
      properties = {
        "spring.json.value.default.type=com.malex.templateresolverservice.model.RssItem"
      })
  public void processMessage(
      RssItem item,
      @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
      @Header(KafkaHeaders.RECEIVED_TOPIC) List<String> topics,
      @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
    log.info("RSS item - {} ", item);
    log.info("topic:{}, partition:{}, offset: {}", topics, partitions, offsets);
  }
}
