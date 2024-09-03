package com.malex.storage_service.consumer.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.malex.storage_service.model.event.RequestItemEvent;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Delivery;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMqConsumer {

  //  private final Receiver receiver;

  private final Flux<Delivery> deliveryForwardFlux;

  private final ObjectMapper jsonMapper;

  @Value("${rabbitmq.queue.forward}")
  private String forwardQueue;

  @PostConstruct
  private void init() {
    consume();
  }

  public Disposable consume() {
    //    return receiver
    //        .consumeAutoAck(forwardQueue)

    return deliveryForwardFlux
        .doOnError(e -> log.error("Receive failed", e))
        .subscribe(
            delivery -> {
              AMQP.BasicProperties properties = delivery.getProperties();
              byte[] body = delivery.getBody();

              // 1. Deserialize byte to json
              String json = (String) SerializationUtils.deserialize(body);

              // 2. map json to Order object
              try {
                RequestItemEvent event = jsonMapper.readValue(json, RequestItemEvent.class);
                log.info(" <<<< properties - {}", properties);
                log.info(" <<<< json - {}", json);
                log.info(" <<<< event - {}", event);

                /*
                 *  Business logic
                 */

              } catch (JsonProcessingException e) {
                e.fillInStackTrace();
              }
            });
  }
}
