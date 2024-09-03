package com.malex.storage_service.configuration.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqJsonMapperConfiguration {

  @Bean
  public ObjectMapper jsonMapper() {
    return new ObjectMapper();
  }
}
