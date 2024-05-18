package com.malex.subscriptionservice.configuration;

import com.malex.subscriptionservice.model.Subscription;
import com.malex.subscriptionservice.property.KafkaConfigurationProperties;
import com.malex.subscriptionservice.property.KafkaProducerConfigurationProperties;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;
import reactor.kafka.sender.SenderOptions;

@Configuration
@RequiredArgsConstructor
public class ReactiveKafkaConfiguration {

  private final KafkaConfigurationProperties configurationProperties;
  private final KafkaProducerConfigurationProperties producerConfigurationProperties;

  /**
   * Configuration SASL_SSL connection: <a
   * href="https://stackoverflow.com/questions/60825373/spring-kafka-application-properties-configuration-for-jaas-sasl-not-working">JAAS/SASL</a>
   */
  @Bean
  public ReactiveKafkaProducerTemplate<String, Subscription> reactiveKafkaProducer() {
    var props = new HashMap<String, Object>();
    props.put(
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, configurationProperties.getBootstrapServer());
    props.put(
        CommonClientConfigs.SECURITY_PROTOCOL_CONFIG,
        configurationProperties.getPropertySecurityProtocol());
    props.put(SaslConfigs.SASL_MECHANISM, configurationProperties.getPropertySaslMechanism());
    props.put(SaslConfigs.SASL_JAAS_CONFIG, configurationProperties.getPropertySaslJaasConfig());

    props.put(
        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
        producerConfigurationProperties.getKeySerializer());
    props.put(
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
        producerConfigurationProperties.getValueSerializer());
    props.put(
        ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG,
        producerConfigurationProperties.getPropertyEnableIdempotence());
    props.put(
        JsonSerializer.ADD_TYPE_INFO_HEADERS,
        producerConfigurationProperties.getPropertySpringJsonAddTypeHeaders());
    return new ReactiveKafkaProducerTemplate<>(SenderOptions.create(props));
  }
}
