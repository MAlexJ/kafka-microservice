package com.malex.rss_reader_service.webservice;

import com.apptasticsoftware.rssreader.Item;
import com.apptasticsoftware.rssreader.RssReader;
import com.malex.rss_reader_service.model.event.RssItemEvent;
import com.malex.rss_reader_service.model.event.SubscriptionEvent;
import com.malex.rss_reader_service.service.RssItemMapperService;
import java.io.IOException;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
@RequiredArgsConstructor
public class RssReaderWebService {

  private final RssItemMapperService service;

  /** Read the latest news on Rss url */
  public Flux<RssItemEvent> readRss(SubscriptionEvent subscription) {
    return Flux.fromStream(openRssStream(subscription.rss()))
        .map(item -> service.mapItemToDtoWithMd5Hash(item, subscription));
  }

  private Stream<Item> openRssStream(String url) {
    try {
      return new RssReader().read(url);
    } catch (IOException e) {
      log.error("Error reading RSS by url - {}, error - {}", url, e.getMessage());
      return Stream.empty();
    }
  }
}
