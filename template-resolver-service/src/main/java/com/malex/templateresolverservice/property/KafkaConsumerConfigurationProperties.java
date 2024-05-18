package com.malex.templateresolverservice.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "kafka.consumer")
public class KafkaConsumerConfigurationProperties {
  private String autoOffsetReset;
  private String groupId;
  private String keyDeserializer;
  private String valueDeserializer;
  private String propertySpringJsonTrustedPackages;
}
