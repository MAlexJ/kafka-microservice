package com.malex.rss_reader_service.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "kafka.producer")
public class KafkaProducerConfigurationProperties {
  private String keySerializer;
  private String valueSerializer;
  private Boolean propertyEnableIdempotence;
  private Boolean propertySpringJsonAddTypeHeaders;
}
