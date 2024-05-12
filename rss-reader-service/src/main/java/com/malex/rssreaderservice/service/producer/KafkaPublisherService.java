package com.malex.rssreaderservice.service.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaPublisherService {

  private final KafkaTemplate<String, Object> kafkaTemplate;

  @Value("${kafka.filter.topic}")
  private String topic;
}
