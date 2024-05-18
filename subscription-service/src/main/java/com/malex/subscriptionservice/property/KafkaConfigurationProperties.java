package com.malex.subscriptionservice.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuration SASL_SSL connection: <a
 * href="https://stackoverflow.com/questions/60825373/spring-kafka-application-properties-configuration-for-jaas-sasl-not-working">JAAS/SASL</a>
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "kafka.server")
public class KafkaConfigurationProperties {
  private String bootstrapServer;
  private String propertySecurityProtocol;
  private String propertySaslMechanism;
  private String propertySaslJaasConfig;
}
