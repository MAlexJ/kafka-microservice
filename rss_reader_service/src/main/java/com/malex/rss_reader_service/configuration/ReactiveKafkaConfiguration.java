package com.malex.rss_reader_service.configuration;

import com.malex.rss_reader_service.model.event.RssItemEvent;
import com.malex.rss_reader_service.model.event.SubscriptionEvent;
import com.malex.rss_reader_service.property.KafkaConfigurationProperties;
import com.malex.rss_reader_service.property.KafkaConsumerConfigurationProperties;
import com.malex.rss_reader_service.property.KafkaProducerConfigurationProperties;
import com.malex.rss_reader_service.property.KafkaTopicConfigurationProperties;
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

/**
 * Configuration SASL_SSL connection: <a
 * href="https://stackoverflow.com/questions/60825373/spring-kafka-application-properties-configuration-for-jaas-sasl-not-working">JAAS/SASL</a>
 */
@Configuration
@RequiredArgsConstructor
public class ReactiveKafkaConfiguration {

  private final KafkaConfigurationProperties properties;
  private final KafkaTopicConfigurationProperties topicProperties;
  private final KafkaConsumerConfigurationProperties consumerProperties;
  private final KafkaProducerConfigurationProperties producerProperties;

  private final String messageEvenDeserializationClass = SubscriptionEvent.class.getName();

  @Bean
  public ReactiveKafkaProducerTemplate<String, RssItemEvent> reactiveKafkaProducer() {
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
  public ReactiveKafkaConsumerTemplate<String, SubscriptionEvent> reactiveKafkaConsumer() {
    var basicReceiverOptions = subscribeOnTopics();
    return new ReactiveKafkaConsumerTemplate<>(basicReceiverOptions);
  }

  private ReceiverOptions<String, SubscriptionEvent> subscribeOnTopics() {
    return buidConsumerReceiverOptions()
        .subscription(Collections.singletonList(topicProperties.getIn()));
  }

  private ReceiverOptions<String, SubscriptionEvent> buidConsumerReceiverOptions() {
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
