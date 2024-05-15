package com.malex.subscriptionservice.configuration;

import com.malex.subscriptionservice.model.Subscription;
import com.malex.subscriptionservice.property.KafkaConfigurationProperty;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;
import reactor.kafka.sender.SenderOptions;

@Configuration
@RequiredArgsConstructor
public class ReactiveKafkaConfiguration {

  private final KafkaConfigurationProperty configurationProperty;

  @Bean
  public ReactiveKafkaProducerTemplate<String, Subscription> reactiveKafkaProducer() {
    var props = new HashMap<String, Object>();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, configurationProperty.getBootstrapServer());
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, false);
    props.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);

    /*
     * Configuration SASL_SSL connection: <a
     * href="https://stackoverflow.com/questions/60825373/spring-kafka-application-properties-configuration-for-jaas-sasl-not-working">JAAS/SASL</a>
     */
    props.put(
        CommonClientConfigs.SECURITY_PROTOCOL_CONFIG,
        configurationProperty.getPropertySecurityProtocol());
    props.put(SaslConfigs.SASL_MECHANISM, configurationProperty.getPropertySaslMechanism());
    props.put(SaslConfigs.SASL_JAAS_CONFIG, configurationProperty.getPropertySaslJaasConfig());
    return new ReactiveKafkaProducerTemplate<>(SenderOptions.create(props));
  }
}
