package com.malex.subscriptionservice.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "kafka.producer")
public class KafkaConfigurationProperty {
  private String bootstrapServer;
  private String propertySecurityProtocol;
  private String propertySaslMechanism;
  private String propertySaslJaasConfig;
}
