package com.malex.templateresolverservice.kafka.producer;

import com.malex.templateresolverservice.model.Message;
import com.malex.templateresolverservice.model.RssItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {

  private final KafkaTemplate<String, Object> kafkaTemplate;

  @Value("${kafka.topic.out}")
  private String topic;

  public void send(String md5Hash, Message message) {
    kafkaTemplate
        .send(topic,md5Hash, message)
        .thenAccept(
            result -> {
              var metadata = result.getRecordMetadata();
              var offset = metadata.offset();
              var partition = metadata.partition();
              log.info(
                  "Message was sent, topic - {}, partition - {}, offset - {}",
                  metadata.topic(),
                  partition,
                  offset);
            });
  }
}
