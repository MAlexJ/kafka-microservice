package com.malex.storage_service.producer.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.malex.storage_service.model.event.ResponseItemEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.Sender;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMqProducer {

  @Value("${rabbitmq.exchange.replay}")
  private String replayExchange;

  @Value("${rabbitmq.routing.key.replay}")
  private String replayRoutingKey;

  private final Sender sender;

  private final ObjectMapper jsonMapper;

  public Mono<ResponseItemEvent> send(ResponseItemEvent event) {
    try {
      // Serialize object to json
      var json = jsonMapper.writeValueAsString(event);

      // Serialize json to bytes
      byte[] orderSerialized = SerializationUtils.serialize(json);

      // Outbound Message that will be sent by the Sender
      var outboundMessageFlux =
          Flux.just(new OutboundMessage(replayExchange, replayRoutingKey, orderSerialized));

      sender
          .sendWithPublishConfirms(outboundMessageFlux)
          .doOnError(e -> log.error("Send failed", e))
          .subscribe(
              outboundMessageResult -> {
                if (outboundMessageResult.isAck()) {
                  log.info("Event delivered - {}, outboundMessageResult -{}", event);
                }
                log.info("Event sent - {}, outboundMessageResult -{}", event);
                log.info("OutboundMessageResult -{}", outboundMessageResult);
              });
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
    }

    // Return posted object to the client
    return Mono.just(event);
  }
}
