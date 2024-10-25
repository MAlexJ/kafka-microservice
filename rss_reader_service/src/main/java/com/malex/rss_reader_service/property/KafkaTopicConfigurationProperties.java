package com.malex.rss_reader_service.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "kafka.topic")
public class KafkaTopicConfigurationProperties {
  private String in;
  private String out;
}