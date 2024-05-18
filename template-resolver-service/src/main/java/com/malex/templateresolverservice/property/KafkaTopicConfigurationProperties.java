package com.malex.templateresolverservice.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "kafka.topic")
public class KafkaTopicConfigurationProperties {
  private String in;
  private String out;
}
