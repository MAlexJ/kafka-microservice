package com.malex.rssreaderservice.webservice;

import com.apptasticsoftware.rssreader.Item;
import com.apptasticsoftware.rssreader.RssReader;
import com.malex.rssreaderservice.model.RssItem;
import com.malex.rssreaderservice.model.Subscription;
import com.malex.rssreaderservice.service.RssItemMapperService;
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
  public Flux<RssItem> readRss(Subscription subscription) {
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
