package com.malex.rssreaderservice.model.event;

import java.util.List;
import lombok.Builder;

@Builder
public record RssItemEvent(

        // todo UUID String uuid; = md5Hash;

    // item info
    String link,
    String title,
    String description,
    String md5Hash,
    // subscription info
    String subscriptionId, // todo remove it
    Long chatId,
    String templateId,
    String customizationId,
    List<String> filterIds) {}
