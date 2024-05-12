package com.malex.rssreaderservice.service;

import com.apptasticsoftware.rssreader.Item;
import com.malex.rssreaderservice.model.RssItem;
import com.malex.rssreaderservice.model.Subscription;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RssItemMapperService {
  private static final String EMPTY_STRING = "";

  private final Md5HashService hashService;

  /** map RSS item to dto with MD5 hash calculation */
  public RssItem mapItemToDtoWithMd5Hash(Item item, Subscription subscription) {
    // item info
    var link = readStingValue(item.getLink());
    var title = readStingValue(item.getTitle());
    var description = readStingValue(item.getDescription());
    // MD5 hash calculation
    var md5Hash = hashService.calculateMd5HashByCriteria(link, title, description);
    return RssItem.builder()
        // item info
        .link(link)
        .title(title)
        .description(description)
        // MD5 hash
        .md5Hash(md5Hash)
        // subscription info
        .subscriptionId(subscription.id())
        .chatId(subscription.chatId())
        .templateId(subscription.templateId())
        .customizationId(subscription.customizationId())
        .filterIds(subscription.filterIds())
        .build();
  }

  public String readStingValue(Optional<String> value) {
    return value.orElse(EMPTY_STRING);
  }
}
