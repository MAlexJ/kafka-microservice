package com.malex.storage_service.configuration.kafka;

import com.malex.storage_service.model.event.ItemEvent;
import com.malex.storage_service.property.KafkaConfigurationProperties;
import com.malex.storage_service.property.KafkaConsumerConfigurationProperties;
import com.malex.storage_service.property.KafkaTopicConfigurationProperties;
import java.util.Collections;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import reactor.kafka.receiver.ReceiverOptions;

@Configuration
@RequiredArgsConstructor
public class ReactiveKafkaConfiguration {

  private final KafkaConfigurationProperties properties;
  private final KafkaTopicConfigurationProperties topicProperties;
  private final KafkaConsumerConfigurationProperties consumerProperties;

  private final String messageEvenDeserializationClass = ItemEvent.class.getName();

  @Bean
  public ReactiveKafkaConsumerTemplate<String, ItemEvent> reactiveKafkaConsumer() {
    var basicReceiverOptions = subscribeOnTopics();
    return new ReactiveKafkaConsumerTemplate<>(basicReceiverOptions);
  }

  private ReceiverOptions<String, ItemEvent> subscribeOnTopics() {
    return buidConsumerReceiverOptions()
        .subscription(Collections.singletonList(topicProperties.getIn()));
  }

  private ReceiverOptions<String, ItemEvent> buidConsumerReceiverOptions() {
    var props = new HashMap<String, Object>();
    // base configuration
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServer());
    props.put(
        CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, properties.getPropertySecurityProtocol());
    props.put(SaslConfigs.SASL_MECHANISM, properties.getPropertySaslMechanism());
    props.put(SaslConfigs.SASL_JAAS_CONFIG, properties.getPropertySaslJaasConfig());
    // consumer configuration
    props.put(ConsumerConfig.GROUP_ID_CONFIG, consumerProperties.getGroupId());
    props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
    props.put(
        JsonDeserializer.TRUSTED_PACKAGES,
        consumerProperties.getPropertySpringJsonTrustedPackages());
    props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, messageEvenDeserializationClass);
    props.put(
        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, consumerProperties.getKeyDeserializer());
    props.put(
        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, consumerProperties.getValueDeserializer());
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, consumerProperties.getAutoOffsetReset());
    return ReceiverOptions.create(props);
  }
}
