package com.malex.templateresolverservice.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "kafka.producer")
public class KafkaProducerConfigurationProperties {
    private String keySerializer;
    private String valueSerializer;
    private Boolean propertyEnableIdempotence;
    private Boolean propertySpringJsonAddTypeHeaders;
}
