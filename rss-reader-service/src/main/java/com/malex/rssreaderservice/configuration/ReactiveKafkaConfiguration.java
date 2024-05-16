package com.malex.rssreaderservice.configuration;

import com.malex.rssreaderservice.property.KafkaConfigurationProperties;
import com.malex.rssreaderservice.property.KafkaConsumerConfigurationProperties;
import com.malex.rssreaderservice.property.KafkaProducerConfigurationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ReactiveKafkaConfiguration {

  private final KafkaConfigurationProperties configurationProperties;
  private final KafkaConsumerConfigurationProperties consumerConfigurationProperties;
  private final KafkaProducerConfigurationProperties producerConfigurationProperties;
}
