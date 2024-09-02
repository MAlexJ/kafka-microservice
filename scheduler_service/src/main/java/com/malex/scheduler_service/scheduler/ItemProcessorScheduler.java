package com.malex.scheduler_service.scheduler;

import com.malex.scheduler_service.model.event.SubscriptionEvent;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemProcessorScheduler {

  @Value("${custom.rabbitmq.exchange.subscription}")
  private String exchange;

  @Value("${custom.rabbitmq.routing.key.subscription}")
  private String routingKey;

  public final RabbitTemplate rabbitTemplate;

  @Async
  @Scheduled(cron = "${scheduled.processing.cron}")
  public void processing() {
    log.info("Start processing cron job");

    var event = new SubscriptionEvent(UUID.randomUUID().toString(), true);

    rabbitTemplate.convertAndSend(
        exchange,
        routingKey,
        event,
        message -> {
          var properties = message.getMessageProperties();
          log.info("Properties - {}", properties);
          /*
           * Set the message expiration.
           * This is a String property per the AMQP 0.9.1 spec.
           * For RabbitMQ, this is a String representation of the message time to live in milliseconds.
           */
          var oneDayInMillis = Long.toString(TimeUnit.DAYS.toMillis(1));
          properties.setExpiration(oneDayInMillis);

          /*
           * Enumeration for the message delivery mode. Can be persistent or non persistent.
           * Use the method 'toInt' to get the appropriate value that is used by the AMQP protocol instead of the ordinal()
           * value when passing into AMQP APIs.
           *
           * NON_PERSISTENT = 1
           * PERSISTENT = 2
           * default = -1
           */
          properties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
          return message;
        });
  }
}
