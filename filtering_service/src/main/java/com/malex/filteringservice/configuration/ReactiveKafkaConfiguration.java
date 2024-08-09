package com.malex.filteringservice.configuration;

import com.malex.filteringservice.model.event.ItemEvent;
import com.malex.filteringservice.property.KafkaConfigurationProperties;
import com.malex.filteringservice.property.KafkaConsumerConfigurationProperties;
import com.malex.filteringservice.property.KafkaProducerConfigurationProperties;
import com.malex.filteringservice.property.KafkaTopicConfigurationProperties;
import java.util.Collections;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.SenderOptions;

@Configuration
@RequiredArgsConstructor
public class ReactiveKafkaConfiguration {

  private final KafkaConfigurationProperties properties;
  private final KafkaTopicConfigurationProperties topicProperties;
  private final KafkaConsumerConfigurationProperties consumerProperties;
  private final KafkaProducerConfigurationProperties producerProperties;

  private final String messageEvenDeserializationClass = ItemEvent.class.getName();

  @Bean
  public ReactiveKafkaProducerTemplate<String, ItemEvent> reactiveKafkaProducer() {
    var props = new HashMap<String, Object>() {};
    // base configuration
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServer());
    props.put(
        CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, properties.getPropertySecurityProtocol());
    props.put(SaslConfigs.SASL_MECHANISM, properties.getPropertySaslMechanism());
    props.put(SaslConfigs.SASL_JAAS_CONFIG, properties.getPropertySaslJaasConfig());
    // producer configuration
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, producerProperties.getKeySerializer());
    props.put(
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, producerProperties.getValueSerializer());
    props.put(
        ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG,
        producerProperties.getPropertyEnableIdempotence());
    props.put(
        JsonSerializer.ADD_TYPE_INFO_HEADERS,
        producerProperties.getPropertySpringJsonAddTypeHeaders());
    return new ReactiveKafkaProducerTemplate<>(SenderOptions.create(props));
  }

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
