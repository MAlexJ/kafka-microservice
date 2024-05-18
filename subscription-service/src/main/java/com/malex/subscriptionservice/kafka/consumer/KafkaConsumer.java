package com.malex.subscriptionservice.kafka.consumer;

import com.malex.subscriptionservice.property.KafkaTopicProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {

  private final KafkaTopicProperties topicProperties;
}
