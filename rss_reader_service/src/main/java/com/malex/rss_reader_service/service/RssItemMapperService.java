package com.malex.rss_reader_service.service;

import com.apptasticsoftware.rssreader.Item;
import com.malex.rss_reader_service.model.event.RssItemEvent;
import com.malex.rss_reader_service.model.event.SubscriptionEvent;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RssItemMapperService {
  private static final String EMPTY_STRING = "";

  private final Md5HashService hashService;

  /** map RSS item to dto with MD5 hash calculation */
  public RssItemEvent mapItemToDtoWithMd5Hash(Item item, SubscriptionEvent event) {
    // item info
    var link = readStingValue(item.getLink());
    var title = readStingValue(item.getTitle());
    var description = readStingValue(item.getDescription());
    // MD5 hash calculation
    var md5Hash = hashService.calculateMd5HashByCriteria(link, title, description);
    return RssItemEvent.builder()
        // item info
        .link(link)
        .title(title)
        .description(description)
        // MD5 hash
        .md5Hash(md5Hash)
        // subscription info
        .subscriptionId(event.id())
        .chatId(event.chatId())
        .templateId(event.templateId())
        .customizationId(event.customizationId())
        .filterIds(event.filterIds())
        .build();
  }

  public String readStingValue(Optional<String> value) {
    return value.orElse(EMPTY_STRING);
  }
}
