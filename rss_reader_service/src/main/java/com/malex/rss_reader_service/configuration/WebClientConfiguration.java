package com.malex.rss_reader_service.configuration;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

/**
 * TODO Remove it, not use
 */
@Configuration
public class WebClientConfiguration {

  public static final int TIMEOUT = 2000;

  @Bean
  public WebClient webClient() {
    final var httpClient =
        HttpClient.create() //
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TIMEOUT) //
            .doOnConnected(
                connection -> {
                  connection.addHandlerLast(new ReadTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS));
                  connection.addHandlerLast(
                      new WriteTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS));
                });

    return WebClient.builder().clientConnector(new ReactorClientHttpConnector(httpClient)).build();
  }
}
